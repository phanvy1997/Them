package vn.khoapham.manager;

import java.io.Serializable;

/**
 * Created by Administrator on 19/11/2017.
 */

public class MonAn {
        public int MaMonAn;
        public String TenMonAn;
        public String GioiThieu;
        public byte[] Anh;

    public MonAn(int maMonAn, String tenMonAn, String gioiThieu, byte[] anh) {
        MaMonAn = maMonAn;
        TenMonAn = tenMonAn;
        GioiThieu = gioiThieu;
        Anh = anh;
    }
}
