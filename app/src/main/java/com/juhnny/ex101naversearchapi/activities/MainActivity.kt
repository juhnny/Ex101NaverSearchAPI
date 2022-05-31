package com.juhnny.ex101naversearchapi.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.juhnny.ex101naversearchapi.R
import com.juhnny.ex101naversearchapi.adapters.NaverSearchAdapter
import com.juhnny.ex101naversearchapi.databinding.ActivityMainBinding
import com.juhnny.ex101naversearchapi.model.NaverSearchResponse
import com.juhnny.ex101naversearchapi.network.RetrofitHelper
import com.juhnny.ex101naversearchapi.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

// 패키지를 나눠 관리하는 방법

//위 패키지명을 수정한 뒤 경고문구를 눌러서 패키지 폴더를 이동시키는 걸 추천
//그냥 드래그앤드롭 하면 기본 패키지 안에도 링크가 남아서 이상해짐.

//R 클래스에 빨간 줄 이 생김.
//R.java는 무조건 기본 패키지의 클래스이기 때문
//import를 추가해준다.


// Retrofit2로 header 요청값을 보내는 방법

class MainActivity : AppCompatActivity() {

    val b by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        b.btnSearch.setOnClickListener { searchData() }
        b.etSearch.setOnEditorActionListener { textView, i, keyEvent ->
            //두번째 파라미터 i : actionId
            when(i){
                EditorInfo.IME_ACTION_SEARCH -> searchData()
            }
            true
        }
    }

    //Naver 검색 API JSON 파싱 작업을 수행할 메소드
    private fun searchData(){
        //소프트 키패드 내리기
        val imm:InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN) //혹은 상수값 0

        val clientId = "eqcqvGF2mKixGnn_gfti"
        val clientSecret = "fUnNrqhDmO"
        var query = b.etSearch.text.toString()

        val retrofit = RetrofitHelper.getRetrofitInstance()
        val retrofitService = retrofit.create(RetrofitService::class.java)
        val call:Call<String> = retrofitService.getShoppingSearchDataByString(clientId, clientSecret, query, 50)
        //Call 객체는 네트워크 작업을 대신하는 명령문들을 갖고 있는 객체. 네트워크 작업은 따로 시작해줘야 한다.
//        call.enqueue(object : Callback<String>{
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                val responseStr = response.body()
//                AlertDialog.Builder(this@MainActivity).setMessage(responseStr).create().show()
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_LONG).show()
//            }
//
//        })

        val call2:Call<NaverSearchResponse> = retrofitService.getShoppingSearchDataByJson(query, 50)
        call2.enqueue(object : Callback<NaverSearchResponse>{
            override fun onResponse(
                call: Call<NaverSearchResponse>,
                response: Response<NaverSearchResponse>
            ) {
                val naverSearchResponse = response.body()
//                AlertDialog.Builder(this@MainActivity)
//                    .setMessage(naverSearchResponse?.total.toString() + "/" + naverSearchResponse?.items?.size)
//                    .create().show()
                naverSearchResponse?.items?.let {
                    b.recycler.adapter = NaverSearchAdapter(this@MainActivity, it)
                }
            }

            override fun onFailure(call: Call<NaverSearchResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}