package scraping;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author Speed Programer
 */
public class Thesource {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        System.out.println("TheSource started");
        String url = "jdbc:mysql://localhost:3306/yazzoopa_scraper?useUnicode=true&characterEncoding=UTF-8";
        String username = "root";
        String password = "raath@aws";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected");
            //////////////////////////
            String urls[] = {
                "http://www.thesource.ca/en-ca/new-at-the-source/c/new-at-the-source?sort=publishingDate&q=%3Arelevance&show=All&text=&view=grid",
                "http://www.thesource.ca/en-ca/featured-deals/c/featured-deals?q=%3Arelevance&show=All&view=grid",};
            String categories[] = {"new-arrivals", "featured-deals"};
            int ss = 0;
            for (int i = 0; i < urls.length; i++) {
                ///// Deleting existing products ///
                String queryCheck = "DELETE FROM items WHERE category = ?";
                PreparedStatement st = connection.prepareStatement(queryCheck);
                st.setString(1, categories[i]);
                int rs = st.executeUpdate();
                //////////////////////
                String urlstring = urls[i];
                Document doc = Jsoup.connect(urlstring).timeout(10 * 1000).get();
                Elements products = doc.getElementsByClass("productListItem");
                for (int item = 0; item < products.size(); item++) {
                    String link = "http://www.thesource.ca" + products.get(item).select("a").first().attr("href");
                    Document subdoc = Jsoup.connect(link).timeout(10 * 1000).get();
                    System.out.println(link);
                    String name = subdoc.getElementsByClass("pdp-name").first().text();
                    String id = subdoc.getElementsByClass("pdp-code").first().getElementsByTag("span").text();
                    String price = subdoc.select("input[name=price]").val();
                    String description = subdoc.getElementsByClass("product-detail-section-content").first().html();
                    String specification = "";
                    if (subdoc.getElementsByClass("product-detail-specs").hasText()) {
                        specification = subdoc.getElementsByClass("product-detail-specs").first().html();
                    }
                    String image_url = "http://www.thesource.ca" + subdoc.getElementsByClass("primary-image").first().attr("src");
                    int in_stock = 0;
                    if (subdoc.hasClass("in-stock")) {
                        in_stock = 1;
                    }
                    String other = "";
                    try {
                        Statement stmt = connection.createStatement();
                        stmt.execute("set names 'utf8'");
                        String sql = "INSERT INTO items (p_id,category,title,status,link,price,image_url,description,specification,other)"
                                + "VALUES(?,?,?,?,?,?,?,?,?,?)";

                        PreparedStatement pstmt = connection.prepareStatement(sql);
                        // Set the values
                        pstmt.setString(1, "thesource_" + id);
                        pstmt.setString(2, categories[i]);
                        pstmt.setString(3, name);
                        pstmt.setInt(4, in_stock);
                        pstmt.setString(5, link);
                        pstmt.setString(6, "$" + price);
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
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Done.");
    }
}
