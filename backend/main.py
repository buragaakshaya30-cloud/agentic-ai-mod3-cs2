from fastapi import FastAPI
from pydantic import BaseModel
from rag_engine import get_llm_response, get_rag_response

app = FastAPI()

class QueryRequest(BaseModel):
    question: str

class ComparisonResponse(BaseModel):
    question: str
    llm_answer: str
    rag_answer: str
    rag_sources: list[str]

@app.post("/compare", response_model=ComparisonResponse)
async def compare_models(request: QueryRequest):
    # 1. Get Standard LLM Answer (Hallucination prone)
    llm_answer = get_llm_response(request.question)

    # 2. Get RAG Answer (Factual/Grounded)
    rag_answer, sources = get_rag_response(request.question)

    return ComparisonResponse(
        question=request.question,
        llm_answer=llm_answer,
        rag_answer=rag_answer,
        rag_sources=sources
    )

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)