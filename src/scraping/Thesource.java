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
            String urls[][] = {
                {"224", "http://www.thesource.ca/en-ca/computers-and-tablets/ipad-devices-and-tablets/c/scc-1-1?q=%3Arelevance&show=All&view=grid"},
                {"247", "http://www.thesource.ca/en-ca/computers-and-tablets/laptops/c/scc-1-2?q=%3Arelevance&show=All&view=grid"},
                {"248", "http://www.thesource.ca/en-ca/computers-and-tablets/desktops/c/scc-1-3?q=%3Arelevance&show=All&view=grid"},
                {"256", "http://www.thesource.ca/en-ca/computers-and-tablets/monitors/c/scc-1-4?q=%3Arelevance&show=All&view=grid"},
                {"262", "http://www.thesource.ca/en-ca/computers-and-tablets/computer-networking/c/scc-1-5?q=%3Arelevance&show=All&view=grid"},
                {"249", "http://www.thesource.ca/en-ca/computers-and-tablets/computer-accessories/c/scc-1-6?q=%3Arelevance&show=All&view=grid"},
                {"258", "http://www.thesource.ca/en-ca/computers-and-tablets/printers-and-scanners/c/scc-1-7?q=%3Arelevance&show=All&view=grid"},
                {"244", "http://www.thesource.ca/en-ca/computers-and-tablets/ereaders/c/scc-1-8?q=%3Arelevance&show=All&view=grid"},
                {"259", "http://www.thesource.ca/en-ca/computers-and-tablets/software/c/scc-1-9?q=%3Arelevance&show=All&view=grid"},
                {"252", "http://www.thesource.ca/en-ca/computers-and-tablets/hard-drives-and-storage/c/scc-1-10?q=%3Arelevance&show=All&view=grid"},
                {"251", "http://www.thesource.ca/en-ca/computers-and-tablets/computer-components-and-parts/c/scc-1-11?q=%3Arelevance&show=All&view=grid"},
                {"525", "http://www.thesource.ca/en-ca/tvs-and-home-theatre/televisions/c/scc-7-1?q=%3Arelevance&show=All&view=grid"},
                {"526", "http://www.thesource.ca/en-ca/tvs-and-home-theatre/projectors-and-screens/c/scc-7-2?q=%3Arelevance&show=All&view=grid"},
                {"527", "http://www.thesource.ca/en-ca/tvs-and-home-theatre/media-streaming/c/scc-7-3?q=%3Arelevance&show=All&view=grid"},
                {"528", "http://www.thesource.ca/en-ca/tvs-and-home-theatre/blu-ray%2c-dvd-players-and-recorders/c/scc-7-4?q=%3Arelevance&show=All&view=grid"},
                {"529", "http://www.thesource.ca/en-ca/tvs-and-home-theatre/satellite-receivers-and-digital-converters/c/scc-7-5?q=%3Arelevance&show=All&view=grid"},
                {"530", "http://www.thesource.ca/en-ca/tvs-and-home-theatre/home-theatre-accessories/c/scc-7-6?q=%3Arelevance&show=All&view=grid"},
                {"531", "http://www.thesource.ca/en-ca/tvs-and-home-theatre/tv-mounts-and-stands/c/scc-7-7?q=%3Arelevance&show=All&view=grid"},
                {"532", "http://www.thesource.ca/en-ca/tvs-and-home-theatre/audio-and-speakers/c/scc-7-8?q=%3Arelevance&show=All&view=grid"},
                {"534", "http://www.thesource.ca/en-ca/cameras-and-camcorders/digital-slr-cameras/c/scc-5-1?q=%3Arelevance&show=All&view=grid"},
                {"536", "http://www.thesource.ca/en-ca/cameras-and-camcorders/point-and-shoot-cameras/c/scc-5-2?q=%3Arelevance&show=All&view=grid"},
                {"535", "http://www.thesource.ca/en-ca/cameras-and-camcorders/digital-camcorders/c/scc-5-3?q=%3Arelevance&show=All&view=grid"},
                {"537", "http://www.thesource.ca/en-ca/cameras-and-camcorders/mirrorless-cameras/c/scc-5-4?q=%3Arelevance&show=All&view=grid"},
                {"538", "http://www.thesource.ca/en-ca/cameras-and-camcorders/camera-accessories/c/scc-5-5?q=%3Arelevance&show=All&view=grid"},
                {"539", "http://www.thesource.ca/en-ca/cameras-and-camcorders/camera-lenses/c/scc-5-6?q=%3Arelevance&show=All&view=grid"},
                {"540", "http://www.thesource.ca/en-ca/cameras-and-camcorders/sport-and-action-cams/c/scc-5-7?q=%3Arelevance&show=All&view=grid"},
                {"547", "http://www.thesource.ca/en-ca/health-and-wearable-tech/activity-trackers/c/scc-14-1?q=%3Arelevance&show=All&view=grid"},
                {"548", "http://www.thesource.ca/en-ca/health-and-wearable-tech/smart-watches/c/scc-14-2?q=%3Arelevance&show=All&view=grid"},
                {"549", "http://www.thesource.ca/en-ca/health-and-wearable-tech/pet-wearables/c/scc-14-3?q=%3Arelevance&show=All&view=grid"},
                {"550", "http://www.thesource.ca/en-ca/health-and-wearable-tech/wearable-gps/c/scc-14-4?q=%3Arelevance&show=All&view=grid"},
                {"551", "http://www.thesource.ca/en-ca/health-and-wearable-tech/action-cameras/c/scc-14-5?q=%3Arelevance&show=All&view=grid"},
                {"552", "http://www.thesource.ca/en-ca/health-and-wearable-tech/health-and-wellness/c/scc-14-6?q=%3Arelevance&show=All&view=grid"},
                {"553", "http://www.thesource.ca/en-ca/health-and-wearable-tech/watches/c/scc-14-7?q=%3Arelevance&show=All&view=grid"},
                {"554", "http://www.thesource.ca/en-ca/gaming/ps4/c/scc-8-1?q=%3Arelevance&show=All&view=grid"},
                {"555", "http://www.thesource.ca/en-ca/gaming/ps3/c/scc-8-2?q=%3Arelevance&show=All&view=grid"},
                {"556", "http://www.thesource.ca/en-ca/gaming/ps-vita/c/scc-8-3?q=%3Arelevance&show=All&view=grid"},
                {"557", "http://www.thesource.ca/en-ca/gaming/xbox-one/c/scc-8-4?q=%3Arelevance&show=All&view=grid"},
                {"558", "http://www.thesource.ca/en-ca/gaming/xbox-360/c/scc-8-5?q=%3Arelevance&show=All&view=grid"},
                {"559", "http://www.thesource.ca/en-ca/gaming/wii-u-and-wii/c/scc-8-6?q=%3Arelevance&show=All&view=grid"},
                {"560", "http://www.thesource.ca/en-ca/gaming/3ds-and-2ds/c/scc-8-7?q=%3Arelevance&show=All&view=grid"},
                {"561", "http://www.thesource.ca/en-ca/gaming/pc-gaming/c/scc-8-8?q=%3Arelevance&show=All&view=grid"},
                {"562", "http://www.thesource.ca/en-ca/gaming/gaming-accessories/c/scc-8-9?q=%3Arelevance&show=All&view=grid"},
                {"563", "http://www.thesource.ca/en-ca/gaming/headsets/c/scc-8-10?q=%3Arelevance&show=All&view=grid"},
                {"565", "http://www.thesource.ca/en-ca/office-equipment-and-supplies/office-printers-and-scanners/c/scc-12-5?q=%3Arelevance&show=All&view=grid"},
                {"566", "http://www.thesource.ca/en-ca/office-equipment-and-supplies/office-software/c/scc-12-6?q=%3Arelevance&show=All&view=grid"},
                {"567", "http://www.thesource.ca/en-ca/office-equipment-and-supplies/office-security/c/scc-12-7?q=%3Arelevance&show=All&view=grid"},
                {"568", "http://www.thesource.ca/en-ca/office-equipment-and-supplies/office-appliances/c/scc-12-4?q=%3Arelevance&show=All&view=grid"},
                {"569", "http://www.thesource.ca/en-ca/office-equipment-and-supplies/presentation-devices-and-accessories/c/scc-12-2?q=%3Arelevance&show=All&view=grid"},
                {"570", "http://www.thesource.ca/en-ca/office-equipment-and-supplies/business-phones/c/scc-12-3?q=%3Arelevance&show=All&view=grid"},
                {"571", "http://www.thesource.ca/en-ca/office-equipment-and-supplies/office-accessories/c/scc-12-8?q=%3Arelevance&show=All&view=grid"},
                {"572", "http://www.thesource.ca/en-ca/office-equipment-and-supplies/calculators/c/scc-12-1?q=%3Arelevance&show=All&view=grid"},
                {"573", "http://www.thesource.ca/en-ca/office-equipment-and-supplies/ink-and-toner/c/scc-12-9?q=%3Arelevance&show=All&view=grid"},
                {"574", "http://www.thesource.ca/en-ca/office-equipment-and-supplies/paper-supplies/c/scc-12-10?q=%3Arelevance&show=All&view=grid"},
                {"575", "http://www.thesource.ca/en-ca/office-equipment-and-supplies/digital-voice-recorders/c/scc-12-11?q=%3Arelevance&show=All&view=grid"},
                {"576", "http://www.thesource.ca/en-ca/toys-and-hobby/novelty-toys/c/scc-9-1?q=%3Arelevance&show=All&view=grid"},
                {"577", "http://www.thesource.ca/en-ca/toys-and-hobby/ride-on-toys/c/scc-9-2?q=%3Arelevance&show=All&view=grid"},
                {"578", "http://www.thesource.ca/en-ca/toys-and-hobby/educational-toys/c/scc-9-3?q=%3Arelevance&show=All&view=grid"},
                {"579", "http://www.thesource.ca/en-ca/toys-and-hobby/hobby/c/scc-9-4?q=Jrelevance?q=%3Arelevance&show=All&view=grid"},
                {"580", "http://www.thesource.ca/en-ca/toys-and-hobby/drones-and-rc-vehicles/c/scc-9-5?q=%3Arelevance&show=All&view=grid"},
                {"582", "http://www.thesource.ca/en-ca/gps%2c-car-and-marine/gps/c/scc-3-3?q=%3Arelevance&show=All&view=grid"},
                {"583", "http://www.thesource.ca/en-ca/gps%2c-car-and-marine/car-audio-and-electronics/c/scc-3-1?q=%3Arelevance&show=All&view=grid"},
                {"584", "http://www.thesource.ca/en-ca/gps%2c-car-and-marine/marine-audio-and-electronics/c/scc-3-2?q=%3Arelevance&show=All&view=grid"},
                {"585", "http://www.thesource.ca/en-ca/gps%2c-car-and-marine/satellite-radio/c/scc-3-4?q=%3Arelevance&show=All&view=grid"},
                {"586", "http://www.thesource.ca/en-ca/gps%2c-car-and-marine/automotive/c/scc-3-5?q=%3Arelevance&show=All&view=grid"}
            };

            int ss = 0;
            for (int i = 0; i < urls.length; i++) {
                try {
                    String tcarr[] = urls[i][1].split("/");
                    String category_title = tcarr[4];
                    category_title += "=>" + tcarr[5];
                    ///// Deleting existing products ///
                    String queryCheck = "DELETE FROM items WHERE category_title = ?";
                    PreparedStatement st = connection.prepareStatement(queryCheck);
                    st.setString(1, category_title);
                    int rs = st.executeUpdate();
                    //////////////////////
                    String urlstring = urls[i][1];
                    Document doc = Jsoup.connect(urlstring)
                            //                        .header("Accept-Encoding", "gzip, deflate")
                            //                        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                            //                        .maxBodySize(0)
                            .timeout(600000)
                            .get();

                    Elements products = null;
                    products = doc.getElementsByClass("productListItem");
                    if (products.size() == 0) {
                        ///////
                        Element div = doc.getElementsByClass("grid-content").first();
                        String attr = div.toString();
                        String[] tokens = attr.split("data-productcollectionid=\"");
                        String temp = tokens[1];
                        String temp_url = "http://www.thesource.ca/en-ca/search/product-collection?uid=" + temp.split("\" data-categorycode=\"")[0];
                        temp_url += "&categoryCode=" + temp.split("\" data-categorycode=\"")[1].split("\">")[0];
                        Document doc_t = Jsoup.connect(temp_url).timeout(600000).get();
                        products = doc_t.getElementsByClass("related-product-item");
                    }
                    /////////////
                    System.out.println("size=" + products.size() + " url=" + urlstring);
                    for (int item = 0; item < products.size(); item++) {
                        String link = "http://www.thesource.ca" + products.get(item).select("a").first().attr("href");
                        Document subdoc = Jsoup.connect(link).timeout(100 * 1000).get();
//                   System.out.println(link);
//                    System.exit(1);

                        String name = subdoc.getElementsByClass("pdp-name").first().text();
                        String id = subdoc.getElementsByClass("pdp-code").first().getElementsByTag("span").text();
                        String price = subdoc.select("input[name=price]").val();
                        String description = "";
                        try {
                            description = subdoc.getElementsByClass("product-details-summary").first().getElementsByTag("p").first().text();
                        } catch (Exception e) {
                        }
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
                            String sql = "INSERT INTO items (p_id,category_title,title,status,link,price,image_url,description,specification,other)"
                                    + "VALUES(?,?,?,?,?,?,?,?,?,?)";

                            PreparedStatement pstmt = connection.prepareStatement(sql);
                            // Set the values
                            pstmt.setString(1, "thesource_" + id);
                            pstmt.setString(2, category_title);
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
