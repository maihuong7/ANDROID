package com.example.drink_app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.drink_app.R;
import com.example.drink_app.ultil.CheckConnection;
import com.example.drink_app.ultil.Server;

import java.util.HashMap;
import java.util.Map;

public class Thongtinthemsanpham extends AppCompatActivity {
    EditText edttensanpham, edtgiasanpham, edthinhanhsanpham, edtmotasanpham, edtidloaisanpham;
    Button btnthem, btnhuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinthemsanpham);
        Anhxa();
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            Evenbutton();
        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
        }
    }

    private void Evenbutton() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edttensanpham.getText().toString().trim();
                String gia = edtgiasanpham.getText().toString().trim();
                String hinhanh = edthinhanhsanpham.getText().toString().trim();
                String mota = edtmotasanpham.getText().toString().trim();
                String id = edtidloaisanpham.getText().toString().trim();
                if (ten.length()>0 && gia.length()>0 && hinhanh.length()>0 && mota.length()>0 && id.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdanthemsanpham, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.trim().equals("success")){
                                Toast.makeText(Thongtinthemsanpham.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(Thongtinthemsanpham.this, "Thêm lỗi!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Thongtinthemsanpham.this, "Hãy kiểm tra dữ liệu!", Toast.LENGTH_SHORT).show();

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("tensanpham",ten);
                            hashMap.put("giasanpham",gia);
                            hashMap.put("hinhanhsanpham",hinhanh);
                            hashMap.put("motasanpham",mota);
                            hashMap.put("idsanpham",id);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Hãy kiểm tra lại dữ liệu");
                }
            }
        });
    }

    private void Anhxa() {
        edttensanpham = (EditText) findViewById(R.id.edittextthemtensp);
        edtgiasanpham = (EditText) findViewById(R.id.edittextthemgiasp);
        edthinhanhsanpham = (EditText) findViewById(R.id.edittextthemanhsp);
        edtmotasanpham = (EditText) findViewById(R.id.edittextthemmotasp);
        edtidloaisanpham = (EditText) findViewById(R.id.edittextthemidsp);
        btnthem = (Button) findViewById(R.id.buttonxacnhanthem);
        btnhuy = (Button) findViewById(R.id.buttontrovethem);

    }
}