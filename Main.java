package StockLights;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.net.ssl.SSLContext;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;

import io.github.zeroone3010.yahueapi.Color;
import io.github.zeroone3010.yahueapi.Hue;
import io.github.zeroone3010.yahueapi.HueBridge;
import io.github.zeroone3010.yahueapi.Room;
import io.github.zeroone3010.yahueapi.State;
import io.github.zeroone3010.yahueapi.discovery.HueBridgeDiscoveryService;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StockLights {

	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
		Future<List<HueBridge>> bridgesFuture = new HueBridgeDiscoveryService()
		        .discoverBridges(bridge -> System.out.println("Bridge found: " + bridge));
		final List<HueBridge> bridges = bridgesFuture.get();
		if( !bridges.isEmpty() ) {
		  final String bridgeIp = bridges.get(0).getIp();
		  System.out.println("Bridge found at " + bridgeIp);
	
		}
		
		final String bridgeIp = "192.168.1.24"; // Fill in the IP address of your
		final String appName = "StockLightsApp"; // Fill in the name of yourapplication 
		final String apiKey = "Wxf572Ibuj-h-fM-Z7R-z2N4yGyRWt8Q2r75HL4b";
		// Push the button on your Hue Bridge to resolve the apiKey future: final 
		Hue hue = new Hue(bridgeIp, apiKey); 
		final Room room = hue.getRoomByName("Dorm").get();
		room.setState(State.builder().color(Color.of(java.awt.Color.BLUE)).on());
		room.setBrightness(254); System.setProperty("webdriver.gecko.driver",
		"D:\\HDDdownloads\\geckodriver.exe"); ProfilesIni ini = new ProfilesIni();
		FirefoxProfile ff = ini.getProfile("robin"); FirefoxOptions options = new
		FirefoxOptions(); options.setHeadless(false);
		ff.setPreference("general.useragent.override",
		"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:86.0) Gecko/20100101 Firefox/86.0"
		); options.addPreference("javascript.enabled", true);
		ff.setPreference("dom.webdriver.enabled", false);
		ff.setPreference("dom.navigator.plugins", "1");
		ff.setPreference("useAutomationExtension", false); options.setProfile(ff);
		
		
		WebDriver drive = new FirefoxDriver(options);
	    Thread.sleep(5000); 
        LocalTime open = new LocalTime("08:30:40"); 
        LocalTime sleep = new LocalTime("15:00:02"); 
        LocalTime now = LocalTime.now(); 
        Timer getValue = new Timer(); 
        getValue.schedule(new TimerTask() {
		
		@Override public void run() {
		
		if (now.isAfter(open) && now.isBefore(sleep)) { 
            if (LocalDate.now().getDayOfWeek() != 1 && LocalDate.now().getDayOfWeek() != 7) { 
                String port = drive.findElement(By.xpath("//*[@id=\"react_root\"]/main/div[2]/div/div/div/div/div/main/div/div[1]/section[1]/header/div[2]/span[1]/span/span[1]")).getText(); 
                if (port.contains("+")) {
		            room.setState(State.builder().color(Color.of(java.awt.Color.BLUE)).on());
		            room.setBrightness(254); 
                    System.out.println(port); 
                } 
                else if (port.contains("-")) { 
                    room.setState(State.builder().color(Color.of(117, 2 , 253)).on()); 
                    room.setBrightness(254);
		            System.out.println(port);
		
		}
		
		}
		
		}
		
		}
		
		}, 0, 300000);
		
	   
	}

}
