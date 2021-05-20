import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxItem;

public class Main {
    public static void main(String[] args) {
        BoxAPIConnection api = new BoxAPIConnection(System.getenv("BOX_TOKEN"));
        // フォルダ一覧取得
        BoxFolder rootFolder = BoxFolder.getRootFolder(api);
        for (BoxItem.Info itemInfo : rootFolder) {
            // ルートフォルダ名出力
            System.out.format("[%s] %s\n", itemInfo.getID(), itemInfo.getName());

            // 子のファイル出力
            getChildFolder(api, itemInfo.getID(), "");
        }
    }

    private static void getChildFolder(BoxAPIConnection api, String id, String mark) {
        if(id == null || id.equals("")) {
            return;
        }

        mark = mark.concat("+");
        BoxFolder folder = new BoxFolder(api, id);
        if (folder.getChildren() != null ) {
            for(BoxItem.Info info : folder) {
                System.out.format(mark + "[%s] %s\n", info.getID(), info.getName());
                // ファイル以外の場合は再帰で取得
                if(!"file".equals(info.getType())) {
                    getChildFolder(api, info.getID(), mark);
                }
            }
        }
    }
}
