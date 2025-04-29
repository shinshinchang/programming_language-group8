import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FirebaseRestUploader {
    public static void main(String[] args) {
        try {
            // 你的Firebase Realtime Database網址（後面加上/vendors.json）
            String firebaseUrl = "https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendors.json";

            // 建立連線
            URL url = new URL(firebaseUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // 要上傳的 JSON 資料
            String jsonInputString = """
            {
                "name": "高也椰薑餅屋",
                "tags": "好吃",
                "contact_info": "0912345678",
                "support_mobile_payment": true,
                "description": "歡迎來高爺爺的攤位買薑餅人!"
            }
            """;

            // 寫入資料
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // 取得回應代碼
            int code = conn.getResponseCode();
            System.out.println("Firebase Response Code: " + code);

            if (code == 200) {
                System.out.println("✅ 資料成功新增到 Firebase！");
            } else {
                System.out.println("⚠️ 發生錯誤，回應代碼：" + code);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
