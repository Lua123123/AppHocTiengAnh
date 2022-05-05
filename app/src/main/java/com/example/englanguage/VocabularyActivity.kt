package com.example.englanguage

import android.app.Dialog
import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.englanguage.adapter.ListVocabularyAdapter
import com.example.englanguage.model.vocabulary.SuccessVocabulary
import com.example.englanguage.model.vocabulary.Vocabulary
import com.example.englanguage.viewmodel.VocabularyViewModel
import de.hdodenhof.circleimageview.CircleImageView

class VocabularyActivity : AppCompatActivity() {
    private val context: Context = this@VocabularyActivity
    private var recyclerView: RecyclerView? = null
    private var adapter: ListVocabularyAdapter? = null
    private val mListVocabulary: List<SuccessVocabulary> = ArrayList()
    private var progressBar: ProgressBar? = null
    private var layoutManager: LinearLayoutManager? = null
    private val page = 1
    private val currentPage = 3
    private val handler = Handler()
    private var isLoading = false
    private val vocabulary: Vocabulary? = null
    private var vocabularyViewModel: VocabularyViewModel? = null
    private val search: String? = null
    private var searchView: SearchView? = null
    private var addVocabulary: CircleImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vocabulary)
        addVocabulary = findViewById(R.id.addVocabulary)
        layoutManager = LinearLayoutManager(this)
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView?.setLayoutManager(layoutManager)
        recyclerView?.setAdapter(adapter)
        adapter = ListVocabularyAdapter(context)
        val itemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        itemDecoration.setDrawable(this.resources.getDrawable(R.drawable.divider_rcv))
        recyclerView?.addItemDecoration(itemDecoration)
        vocabularyViewModel = VocabularyViewModel(
            mListVocabulary,
            recyclerView,
            adapter,
            progressBar,
            layoutManager,
            page,
            currentPage,
            handler,
            isLoading,
            context,
            search
        )
        vocabularyViewModel!!.clickGetVocabulary(page)
        vocabularyViewModel!!.addEventLoad()

        val intent = intent
        val bundle = intent.extras
        var authorization: String? = bundle!!.getString("Authorization")

        addVocabulary?.setOnClickListener(View.OnClickListener {
            vocabularyViewModel!!.openDialogInsertVocabulary(Gravity.CENTER, authorization)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView!!.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView!!.maxWidth = Int.MAX_VALUE
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                adapter!!.filter.filter(s)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                adapter!!.filter.filter(s)
                return false
            }
        })
        return true
    }

    //HÀM KHI BẤM VÀO SEARCH VÀ TẮT BÀN PHÍM ĐI
    //THÌ NHẤN NEXT SẼ THOÁT NÚT SEARCH, NEXT LẦN NỮA MỚI THOÁT RA GIAO DIỆN KHÁC
    //THAY VÌ ĐANG TRONG CHẾ ĐỘ SEARCH NHẤN NEXT NÓ VỀ GIAO DIỆN KHÁC THÌ KHÔNG ĐÚNG
    override fun onBackPressed() {
        if (!searchView!!.isIconified) {
            searchView!!.isIconified = true
            return
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (adapter != null) {
            adapter!!.release()
        }
    }
}