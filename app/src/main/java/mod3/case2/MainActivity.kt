package mod3.case2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import mod3.case2.api.RetrofitClient
import mod3.case2.models.RequestModel

class MainActivity : AppCompatActivity() {

    private lateinit var etQuery: EditText
    private lateinit var btnCompare: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var resultsContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etQuery = findViewById(R.id.etQuery)
        btnCompare = findViewById(R.id.btnCompare)
        progressBar = findViewById(R.id.progressBar)
        resultsContainer = findViewById(R.id.resultsContainer)

        btnCompare.setOnClickListener {
            val query = etQuery.text.toString().trim()
            if (query.isNotEmpty()) {
                performComparison(query)
            } else {
                Toast.makeText(this, "Please enter a query", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performComparison(query: String) {
        progressBar.visibility = View.VISIBLE
        resultsContainer.removeAllViews()

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.compareModels(RequestModel(query))

                // Display Standard LLM Result
                addResultCard("Standard LLM Answer", response.llmAnswer)

                // Display RAG Result
                val ragText = "${response.ragAnswer}\n\nSources: ${response.ragSources.joinToString()}"
                addResultCard("RAG System Answer", ragText)

            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun addResultCard(title: String, content: String, isRag: Boolean = false) {
        val view = layoutInflater.inflate(R.layout.item_result, resultsContainer, false)

        val colorStrip = view.findViewById<View>(R.id.colorStrip)

        // Change color based on system type
        if (isRag) {
            colorStrip.setBackgroundColor(resources.getColor(R.color.rag_accent, null))
        } else {
            colorStrip.setBackgroundColor(resources.getColor(R.color.llm_accent, null))
        }

        view.findViewById<TextView>(R.id.tvTitle).text = title
        view.findViewById<TextView>(R.id.tvContent).text = content
        resultsContainer.addView(view)
    }
}