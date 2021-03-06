package tiregdev.hi_depok.utils;

/**
 * Created by SONY-VAIO on 10/5/2017.
 */

public class AppConfig {
    // Server user login url
    public static String URL_LOGIN = "http://hi.depok.go.id/android_api/login.php";

    // Server user register url
    public static String URL_REGISTER = "http://hi.depok.go.id/android_api/register.php";

    // Server user update url
    public static String URL_UPDATE = "http://hi.depok.go.id/android_api/update.php";

    // Server user register url
    public static String DISPLAY_MODUL = "http://hi.depok.go.id/android_api/masterpiece/display_modul.php";

    // Server submit post
    public static String SUBMIT_MASTERPIECE = "http://hi.depok.go.id/android_api/masterpiece/submit.php";

    // Server display post
    public static String DISPLAY_MASTERPIECE = "http://hi.depok.go.id/android_api/masterpiece/display_masterpiece.php";

    // Server display komentar
    public static String DISPLAY_KOMENTAR_MASTERPIECE = "http://hi.depok.go.id/android_api/masterpiece/display_komentar_masterpiece.php";

    // Server like post
    public static String LIKE_POST = "http://hi.depok.go.id/android_api/masterpiece/insert_suka.php";

    // Server check like
    public static String CHECK_LIKE = "http://hi.depok.go.id/android_api/masterpiece/check_user_like.php";

    // Server insert komentar
    public static String INSERT_KOMENTAR = "http://hi.depok.go.id/android_api/masterpiece/insert_komentar.php";

    // Server user register url
    public static String DISPLAY_CARIDATA = "http://hi.depok.go.id/android_api/caridata/display_caridata.php";


    public static String IMG_LINK = "http://hi.depok.go.id/img/";

    public static String IMG_MASTERPIECE = "http://hi.depok.go.id/storage/img_masterpiece/";

    // GCM URL -
    public static final String BASE_URL = "http://hidepok.id/api/v1";
    public static final String LOGIN = BASE_URL + "/user/login";
    public static final String REGISTER = BASE_URL + "/user/register";
    public static final String UPDATE = BASE_URL + "/user/update";
    public static final String USER = BASE_URL + "/user/_ID_";
    public static final String CHAT_ROOMS = BASE_URL + "/chat_rooms";
    public static final String CHAT_THREAD = BASE_URL + "/chat_rooms/_ID_";
    public static final String CHAT_THREAD_ALL = BASE_URL + "/chat_rooms/all/_ID_";
    public static final String CHAT_ROOM_MESSAGE = BASE_URL + "/chat_rooms/_ID_/message";
    public static final String CHAT_NEW_THREAD = BASE_URL + "/chat_rooms/new";
    public static final String NOTIF = BASE_URL + "/notifications/_ID_";
    public static final String CARI_DATA = BASE_URL + "/caridata/";
    public static final String SENTIMENT = BASE_URL + "/sentiment";
    public static final String KRITIK_SARAN = BASE_URL + "/kritik_saran/new";
}