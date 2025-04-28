import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FirebaseService {
    private static Firestore db;

    
    public static void initialize() {
        try {
            FileInputStream serviceAccount = new FileInputStream("nccu-market-firebase-adminsdk-fbsvc-89ab8bf73a.json");
            

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId("nccu-market") 
                    .build();

            FirebaseApp.initializeApp(options);
            db = FirestoreClient.getFirestore();

            System.out.println("Firebase 初始化成功！");
            System.out.println("目前連到的Project ID：" + FirebaseApp.getInstance().getOptions().getProjectId());
        } catch (IOException e) {
            System.out.println("Firebase 初始化失敗！");
            e.printStackTrace();
        }
    }

    
    public static void addOrUpdateVendor(String stallId, String name, String tag, String description, String contactInfo, boolean mobilePayment) {
        try {
            Map<String, Object> vendorData = new HashMap<>();
            vendorData.put("name", name);
            vendorData.put("tag", tag);
            vendorData.put("description", description);
            vendorData.put("contact_info", contactInfo);
            vendorData.put("support_mobile_payment", mobilePayment);

            
            DocumentReference docRef = db.collection("vendors").document(stallId);

            ApiFuture<WriteResult> result = docRef.set(vendorData);

            System.out.println("攤販資料新增/更新成功！寫入時間：" + result.get().getUpdateTime());

        } catch (Exception e) {
            System.out.println("攤販資料新增/更新失敗！");
            e.printStackTrace();
        }
    }
}
