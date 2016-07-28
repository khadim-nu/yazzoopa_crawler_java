package scraping;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author
 */
public class Gencomarketplace {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // System.out.println("Marks started");
        String url = "jdbc:mysql://localhost:3306/yazzoopa_scraper?useUnicode=true&characterEncoding=UTF-8";
        String username = "root";
        String password = "raath@aws";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            //  System.out.println("Database connected");
            //////////////////////////
            String p1 = "http://www.gencomarketplace.com/shop/SearchDisplay?searchSource=Q&orderBy=10&setDefaultSearch=**&showResultsPage=true&langId=-1&beginIndex=0&sType=SimpleSearch&pageSize=8&resultCatEntryType=&catalogId=11651&pageView=list&searchTerm=&storeId=12002&facet=ads_f7_ntk_cs:%22Brampton,%20ON-10281%22#facet:&productBeginIndex:";
            String p2 = "&orderBy:10&pageView:list&minPrice:&maxPrice:&pageSize:undefined&";

            Document doc = Jsoup.connect(p1 + "0" + p2)
                    .header("Accept-Encoding", "gzip, deflate")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                    .maxBodySize(0)
                    .timeout(600000)
                    .get();
            Elements products = null;
            //////////////
            products = doc.getElementsByClass("num_products");
            String temp_t = products.get(0).text();
            temp_t = temp_t.split("of")[1];
            temp_t = temp_t.replace(",", "");
            int total_products = Integer.parseInt(temp_t.trim());
            //1 - 8 of 4,827

//            System.exit(1);
            for (int i = 0; i <= total_products; i = i + 8) {
                try {
                    String pageurl = "http://www.gencomarketplace.com/shop/ProductListingView?searchTermScope=&searchType=1000&filterTerm=&langId=-1&advancedSearch=&sType=SimpleSearch&gridPosition=&metaData=&manufacturer=&ajaxStoreImageDir=%2Fwcsstore%2FGencoMarketPlace%2F&resultCatEntryType=&catalogId=11651&searchTerm=**&resultsPerPage=8&emsName=&facet=ads_f7_ntk_cs%3A%22Brampton%2C+ON-10281%22&categoryId=&storeId=12002&enableSKUListView=false&disableProductCompare=false&ddkey=ProductListingView_6_-2011_1410&filterFacet=";
                    doc = Jsoup.connect(pageurl)
                            .data("contentBeginIndex", "0")
                            .data("productBeginIndex", "" + i)
                            .data("beginIndex", "" + i)
                            .data("orderBy", "10")
                            .data("facetId", "")
                            .data("pageView", "list")
                            .data("resultType", "products")
                            .data("orderByContent", "")
                            .data("searchTerm", "")
                            .data("facet", "")
                            .data("facetLimit", "")
                            .data("minPrice", "")
                            .data("maxPrice", "")
                            .data("storeId", "12002")
                            .data("catalogId", "11651")
                            .data("langId", "-1")
                            .data("enableSKUListView", "")
                            .data("widgetPrefix", "6_1410")
                            .data("isWhatsNew", "")
                            .data("objectId", "_6_-2011_1410")
                            .data("requesttype", "ajax")
                            .header("Accept-Encoding", "gzip, deflate")
                            .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                            .maxBodySize(0)
                            .timeout(600000)
                            .post();

                    products = null;
                    products = doc.getElementsByClass("product_info");
                    System.out.println("-------------Page=" + i + "-------------------------------------------------");
//                    System.exit(1);
                    String category_title = "";
                    //////////////////////
                    for (Element prod : products) {

                        String link = prod.select("a").first().attr("href");

                        System.out.println(link);

                        Document subdoc = Jsoup.connect(link).timeout(100 * 1000).get();

                        String name = subdoc.getElementsByClass("titleItems").first().text();

                        String id = "gencomarketplace_" + name;
                        String price = subdoc.getElementsByClass("price_offer").get(0).text().split("CAD")[1].replace("Shipping:", "").trim();
                        //Purchase Price:  CAD  $527.63 Shipping:   CAD  $50.00
                        String image_url = "http:" + subdoc.getElementById("productMainImage").attr("src");

                        String description = subdoc.getElementsByClass("product_longdescription").get(0).text();
                        category_title = subdoc.getElementById("pdtManifest").getElementsByTag("td").get(2).text();
                        String specification = "";
                        String other = "";
                        System.out.println(category_title);
//                        System.exit(1);
                        try {
                            ///// Deleting existing products ///
                            String queryCheck = "DELETE FROM items WHERE p_id = ?";
                            PreparedStatement st = connection.prepareStatement(queryCheck);
                            st.setString(1, id);
                            int rs = st.executeUpdate();
                            //////////////////////

                            Statement stmt = connection.createStatement();
                            stmt.execute("set names 'utf8'");
                            String sql = "INSERT INTO items (p_id,category_title,title,status,link,price,image_url,description,specification,other)"
                                    + "VALUES(?,?,?,?,?,?,?,?,?,?)";

                            PreparedStatement pstmt = connection.prepareStatement(sql);
                            // Set the values
                            pstmt.setString(1, id);
                            pstmt.setString(2, category_title);
                            pstmt.setString(3, name);
                            pstmt.setInt(4, 1);
                            pstmt.setString(5, link);
                            pstmt.setString(6, price);
                            pstmt.setString(7, image_url);
                            pstmt.setString(8, description);
                            pstmt.setString(9, specification);
                            pstmt.setString(10, other);
                            // Insert 
                            pstmt.executeUpdate();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
//                    System.exit(1);
                } catch (Exception e) {
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Done.");
    }
}
