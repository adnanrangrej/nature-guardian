import os
from google import genai
from google.api_core.exceptions import GoogleAPIError
from google.genai import types

GEMINI_MODEL = os.environ.get('GEMINI_MODEL', 'gemini-2.0-flash')

client = genai.Client()

# Default system instruction (fallback)
DEFAULT_SYSTEM_INSTRUCTION = "You are NatureBot, an AI conservation assistant. Answer clearly and politely. Explain biodiversity, species threats, and conservation topics in a simple and engaging way, suitable for students, researchers, and nature lovers. Use examples from India when relevant and possible, but prioritize global accuracy. Avoid technical jargon unless asked."


def lambda_handler(event, context):
    try:
        print(event)
        # Extract prompt
        if 'prompt' not in event or not isinstance(event.get('prompt'), str):
            return error_response("Missing or invalid 'prompt'", status_code=400, context=context)

        prompt = event.get('prompt')
        if not isinstance(prompt, str):
            return error_response("Prompt must be a string", status_code=400, context=context)

        # Extract history
        history = event.get('history', [])

        # Extract system_instruction
        system_instruction = event.get('systemInstruction', DEFAULT_SYSTEM_INSTRUCTION)
        if not isinstance(system_instruction, str):
            print(f"Invalid system_instruction: {system_instruction}. Setting to default.")
            system_instruction = DEFAULT_SYSTEM_INSTRUCTION

        # Extract max_tokens
        max_tokens = event.get('max_tokens', 512)
        if not isinstance(max_tokens, int) or not (10 <= max_tokens <= 2048):
            print(f"Invalid max_tokens: {max_tokens}. Setting to default 512.")
            max_tokens = 512

        # Convert history to Gemini format
        gemini_history = convert_history(history)

        # Prompt content
        prompt_content = types.Content(role='user', parts=[types.Part(text=prompt)])

        # Finalize the history with the prompt
        full_contents = gemini_history + [prompt_content]

        # Get Response from Gemini
        response = client.models.generate_content(
            model=GEMINI_MODEL,
            contents=full_contents,
            config=types.GenerateContentConfig(
                system_instruction=system_instruction,
                temperature=0.7,
                top_p=0.9,
                top_k=40,
                candidate_count=1,
                max_output_tokens=max_tokens,
                response_mime_type="text/plain",
                presence_penalty=0.3,
                frequency_penalty=0.2,
            )
        )

        # Safely extract role
        role = 'model'
        if response.candidates and hasattr(response.candidates[0], 'content') and \
                response.candidates[0].content:
            role = response.candidates[0].content.role or 'model'

        # Extract request id
        request_id = context.aws_request_id if context else "unknown"

        # Build Body
        response_body = {
            'response': {
                'text': response.text,
                'role': role
            },
            'is_error': False,
            'error': None
        }

        # Success response
        return {
            'statusCode': 200,
            'body': response_body,
            'request_id': request_id
        }
    except ValueError as ve:
        return error_response(str(ve), status_code=400, context=context)
    except GoogleAPIError as gae:
        return error_response(f"Gemini API error: {str(gae)}", status_code=502, context=context)
    except Exception as e:
        return error_response(f"Internal server error: {str(e)}", status_code=500, context=context)


def convert_history(history):
    gemini_history = []
    # Check if history is a list
    if not isinstance(history, list):
        print("History is not a list. No History will be sent.")
        return gemini_history

    # Iterate through each message in the history
    for message in history:
        # Check if message is a dictionary or not or if it has the required keys
        if not isinstance(message, dict) or 'role' not in message or 'content' not in message:
            print(f"Warning: Skipping invalid message format in history: {message}")
            continue

        # Extract role and content from the message
        role = message.get('role')
        content = message.get('content')

        # Check if role is valid and content is a string
        if role not in ['user', 'model']:
            print(f"Warning: Skipping message with invalid role: {role}")
            continue
        if not isinstance(content, str):
            print(f"Warning: Skipping message with non-string content: {content}")
            continue

        # Convert role to Gemini format
        text_part = types.Part(text=content)
        content_item = types.Content(role=role, parts=[text_part])
        gemini_history.append(content_item)

    return gemini_history


# Error response helper
def error_response(message, status_code=500, context=None):
    return {
        'statusCode': status_code,
        'body': {
            'response': None,
            'is_error': True,
            'error': message
        },
        'request_id': context.aws_request_id if context else "unknown"
    }
