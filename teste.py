import ollama
response = ollama.chat(model="sqlcoder", messages=[
    {
        "role": "user",
        "content": "me diga o nome de todos os alunos com menos de 18 anos"
    }
])

print(response["message"]["content"])