package mod3.case2.models

import com.google.gson.annotations.SerializedName

data class ResponseModel(
    @SerializedName("question") val question: String,
    @SerializedName("llm_answer") val llmAnswer: String,
    @SerializedName("rag_answer") val ragAnswer: String,
    @SerializedName("rag_sources") val ragSources: List<String>
)