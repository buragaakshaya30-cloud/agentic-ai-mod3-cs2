# In a real scenario, you would load your vector DB here
def get_llm_response(question: str) -> str:
    # Simulating a standard LLM response
    return f"Standard LLM: Based on my training data, the answer to '{question}' is likely X, Y, Z. (Note: May contain hallucinations)"

def get_rag_response(question: str) -> tuple[str, list[str]]:
    # Simulating RAG retrieval from a knowledge base
    sources = ["Document_A.pdf", "Wiki_Article_1"]
    answer = f"RAG System: According to the retrieved documents, the factual answer to '{question}' is specifically A, B, C."
    return answer, sources