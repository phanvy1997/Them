package vn.khoapham.manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 19/11/2017.
 */

public class AdapterMonAn extends BaseAdapter{
    Activity context;
    ArrayList<MonAn> list;
    String id;

    public AdapterMonAn(Activity context, ArrayList<MonAn> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listview_row, null);
        ImageView imgHinhDaiDien = (ImageView) row.findViewById(R.id.imgHinhDaiDien);
        TextView txtId = (TextView) row.findViewById(R.id.txtId);
        TextView txtTen = (TextView) row.findViewById(R.id.txtTen);
        TextView txtSdt = (TextView) row.findViewById(R.id.txtSdt);
        Button btnXoa = (Button) row.findViewById(R.id.btnXoa);
        Button btnSua = (Button) row.findViewById(R.id.btnSua);

            final MonAn monAn = list.get(position);
        txtId.setText(monAn.MaMonAn + "");
        txtTen.setText(monAn.TenMonAn);
        txtSdt.setText(monAn.GioiThieu);

        Bitmap bmHinhDaiDien = BitmapFactory.decodeByteArray(monAn.Anh, 0, monAn.Anh.length);
        imgHinhDaiDien.setImageBitmap(bmHinhDaiDien);

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("MaMonAn", monAn.MaMonAn);
                context.startActivity(intent);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa nhân viên này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(monAn.MaMonAn);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return row;
    }

    private void delete(int idMonAn) {
        SQLiteDatabase database = Database.initDatabase(context,"MonNgonVietNamS.sqlite");
        database.delete("MonAn", "MaMonAn = ?", new String[]{idMonAn + ""});
        list.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM MonAn",null);
        list.clear();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int MaMonAn = cursor.getInt(0);
            String TenMonAn = cursor.getString(1);
            String GioiThieu = cursor.getString(2);
            byte[] Anh = cursor.getBlob(3);
            list.add(new MonAn(MaMonAn,TenMonAn,GioiThieu,Anh));
        }
        notifyDataSetChanged();
    }
}
