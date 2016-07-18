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
 * @author Speed Programer
 */
public class Gianttiger {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        System.out.println("gianttiger started");
        String url = "jdbc:mysql://localhost:3306/yazzoopa_scraper?useUnicode=true&characterEncoding=UTF-8";
        String username = "root";
        String password = "raath@aws";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected");
            //////////////////////////
            String urls[] = {
                "http://www.gianttiger.com/category/house_home/bathroom.do?c=7.100382&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/house_home/bedroom.do?c=7.100255&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/house_home/kitchen.do?c=7.100381&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/house_home/furniture.do?c=7.101361&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/house_home/hardware.do?c=7.100384&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/house_home/heating-cooling.do?c=7.101271&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/house_home/home_decor.do?c=7.100259&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/house_home/laundry_cleaning.do?c=7.100383&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/house_home/outdoor.do?c=7.103581&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/house_home/storage.do?c=7.100261&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/house_home/window.do?c=7.100256&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/ladies/tops.do?c=4.100211&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/ladies/bottoms.do?c=4.100213&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/ladies/dresses.do?c=4.100231&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/ladies/basics.do?c=4.100236&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/ladies/sleepwear.do?c=4.100233&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/ladies/activewear.do?c=4.100234&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/ladies/uniforms.do?c=4.100235&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/ladies/footwear.do?c=4.100214&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/ladies/plus.do?c=4.100237&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/ladies/accessories.do?c=4.100232&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/ladies/jewelery.do?c=4.101374&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/ladies/outerwear.do?nType=2&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/men/tops.do?c=6.100167&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/men/bottoms.do?c=6.100238&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/men/basics.do?c=6.100244&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/men/sleepwear.do?nType=2&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/men/footwear.do?c=6.100239&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/men/activewear.do?c=6.100242&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/men/workwear.do?c=6.100243&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/men/accessories.do?c=6.100240&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/men/outerwear.do?c=6.100421&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/kids/boys.do?c=5.101149&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/kids/girls.do?c=5.101150&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/kids/baby.do?c=5.101151&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/lifestyle_entertainment/automotive.do?c=8.100387&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/lifestyle_entertainment/books_dvds.do?c=8.100385&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/lifestyle_entertainment/hobbies.do?c=8.100386&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/lifestyle_entertainment/electronics.do?c=8.100263&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/lifestyle_entertainment/health_beauty.do?c=8.100266&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/lifestyle_entertainment/hunting.do?nType=2&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/lifestyle_entertainment/luggage.do?nType=2&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/lifestyle_entertainment/office.do?c=8.103574&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/lifestyle_entertainment/pets.do?c=8.100216&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/lifestyle_entertainment/sports_fitness.do?c=8.100522&sortby=newArrivalsDescend&pp=250&page=",
                "http://www.gianttiger.com/category/lifestyle_entertainment/toys_games.do?c=8.100264&sortby=newArrivalsDescend&pp=250&page="
            };
            String categories[] = {"333", "334", "344", "337", "350", "353", "340", "342", "351", "352", "350",
                "182", "182", "182", "181", "182", "182", "178", "184", "81", "181", "59", "180",
                "176", "176", "176", "176", "177", "176", "178", "175", "180",
                "171", "171", "42",
                "41", "43", "70", "51", "57", "423", "494", "521", "522", "66", "70"};

            int ss = 0;
            for (int i = 0; i < urls.length; i++) {
                String tcarr[] = urls[i].split("/");
                String category_title = tcarr[4];
                category_title += "=>" + tcarr[5].split("c=")[0].replace(".do?", "");
                ///// Deleting existing products ///
                String queryCheck = "DELETE FROM items WHERE category_title = ?";
                PreparedStatement st = connection.prepareStatement(queryCheck);
                st.setString(1, category_title);
                int rs = st.executeUpdate();
                //////////////////////
                String urlstring = urls[i];
                int page_no = 0;
                boolean more = true;
                while (more) {
                    page_no++;
                    if (i == 0 && page_no == 2) {
                        more = false;
                    }
                    System.out.println(urlstring + page_no);
                    Document doc = Jsoup.connect(urlstring + page_no).timeout(100 * 1000).get();

                    Elements products = doc.getElementsByClass("directoryCell");
                    int ps = products.size();
                    System.out.println(ps);
                    ss += ps;
                    if (ps > 0) {
                        for (Element product : products) {
                            String next_link = "http://www.gianttiger.com" + product.getElementsByClass("thumbheader").first().getElementsByTag("a").attr("href");
                            System.out.println(next_link + page_no);
                            Document subdoc = Jsoup.connect(next_link).timeout(100 * 1000).get();

                            ///////////////////////////////////////////
                            String name = subdoc.title();
                            String id = subdoc.getElementsByClass("itemID").first().text();
                            id = id.replaceAll("Product #", "");
                            String price = product.getElementById("productPricing").text();

                            String description = subdoc.getElementsByClass("default").first().text();
                            String specification = "";
                            try {
                                specification = subdoc.getElementById("tab_01_content").getElementsByTag("ul").html();
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }

                            String image_url = product.getElementsByClass("thumbcontainer").first().getElementsByTag("img").attr("src");
                            int in_stock = 1;
                            String other = "";

                            try {

//                                String queryCheck = "SELECT * from items WHERE p_id = ?";
//                                PreparedStatement st = connection.prepareStatement(queryCheck);
//                                st.setString(1, "gianttiger_" + id);
//                                ResultSet rs = st.executeQuery();
                                // if (!rs.next()) {
                                Statement stmt = connection.createStatement();
                                stmt.execute("set names 'utf8'");
                                String sql = "INSERT INTO items (p_id,category_title,title,status,link,price,image_url,description,specification,other)"
                                        + "VALUES(?,?,?,?,?,?,?,?,?,?)";

                                PreparedStatement pstmt = connection.prepareStatement(sql);
                                // Set the values
                                pstmt.setString(1, "gianttiger_" + id);
                                pstmt.setString(2, category_title);
                                pstmt.setString(3, name);
                                pstmt.setInt(4, in_stock);
                                pstmt.setString(5, next_link);
                                pstmt.setString(6, price);
                                pstmt.setString(7, image_url);
                                pstmt.setString(8, description);
                                pstmt.setString(9, specification);
                                pstmt.setString(10, other);
                                // Insert 
                                pstmt.executeUpdate();
                                //}
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
//                            ////////////////////////////////////////////////
                        }
                    } else {
                        more = false;
                    }
                }
            }
            System.out.println(ss);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(
                "Done.");
    }

}
