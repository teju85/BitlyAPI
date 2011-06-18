import com.blogspot.ficklemind.bitly.*;

class BitlyTest {
    private static void qrLink(Bitly b) throws BitlyException {
        String bitlyLink = "http://bit.ly/cmeH01";
        String qrcode = b.getQRLink(bitlyLink);
        System.out.println("getQRLink - Link="+bitlyLink+" QRLink="+qrcode);
    }

    private static void shorten(Bitly b) throws BitlyException {
        BitlyShorten r = b.shorten("http://www.google.com/mail");
        System.out.println("shorten - LongLink="+r.long_url+" ShortLink="+r.url);
    }

    private static void expand(Bitly b) throws BitlyException {
        BitlyExpand r = b.expand(new String[] { "http://bit.ly/cmeH01" }, null);
        System.out.println("expand - LongLink="+r.expand[0].long_url+" ShortLink="+r.expand[0].short_url);
    }

    private static void validate(Bitly b) throws BitlyException {
        BitlyValidate r = b.validate();
        if(r.valid) {
            System.out.println("validate - login credentials are 'valid'");
        }
        else {
            System.out.println("validate - login credentials are 'invalid'");
        }
    }

    private static void clicks(Bitly b) throws BitlyException {
        BitlyClicks r = b.clicks(new String[] { "http://bit.ly/cmeH01" }, null);
        System.out.println("clicks - "+" ShortLink="+r.expand[0].short_url+" UserClicks="+r.expand[0].user_clicks+" GlobalClicks="+r.expand[0].global_clicks);
    }

    private static void referrers(Bitly b) throws BitlyException {
        BitlyReferrers r = b.referrers("http://bit.ly/cmeH01", true);
        System.out.println("referrers - "+" ShortLink="+r.short_url+" UserClicks="+r.referrers[0].clicks+" URL="+r.referrers[0].url);
    }

    private static void countries(Bitly b) throws BitlyException {
        BitlyCountries r = b.countries("http://bit.ly/cmeH01", true);
        System.out.println("countries - "+" ShortLink="+r.short_url+" Country="+r.countries[0].country+" Clicks="+r.countries[0].clicks);
    }

    private static void clicks_by_minute(Bitly b) throws BitlyException {
        BitlyClicksByMinute r = b.clicks_by_minute(new String[] { "http://bit.ly/cmeH01" }, null);
        System.out.println("clicks_by_minute - "+" ShortLink="+r.clicks_by_minute[0].short_url+" Clicks="+r.clicks_by_minute[0].clicks[0]);
    }

    private static void clicks_by_day(Bitly b) throws BitlyException {
        BitlyClicksByDay r = b.clicks_by_day(new String[] { "http://bit.ly/cmeH01" }, null);
        System.out.println("clicks_by_day - "+" ShortLink="+r.clicks_by_day[0].short_url+
                           " Clicks="+r.clicks_by_day[0].clicks[0].clicks+" dayStart="+r.clicks_by_day[0].clicks[0].day_start);
    }

    private static void bitly_pro_domain(Bitly b) throws BitlyException {
        BitlyBitlyProDomain r = b.bitly_pro_domain("nyti.ms");
        System.out.println("bitly_pro_domain - "+" domain="+r.domain+" pro="+r.bitly_pro_domain);
    }

    private static void lookup(Bitly b) throws BitlyException {
        BitlyLookup r = b.lookup(new String[] { "http://www.google.com/mail" });
        System.out.println("lookup - LongLink="+r.lookup[0].url+" ShortLink="+r.lookup[0].short_url);
    }

    private static void info(Bitly b) throws BitlyException {
        BitlyInfo r = b.info(new String[] { "http://bit.ly/cmeH01" }, null);
        System.out.println("info - "+" ShortLink="+r.info[0].short_url+" Title="+r.info[0].title);
        r = b.info(null, new String[]{ "abcdef" });
        System.out.println("info - "+" ShortLink="+r.info[0].hash+" Title="+r.info[0].title);
    }

    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("USAGE: BitlyTest <login> <api-key>!!");
            return;
        }
        String login = args[0];
        String apiKey = args[1];
        try {
            Bitly b = new Bitly(login, apiKey);
            qrLink(b);
            shorten(b);
            expand(b);
            validate(b);
            clicks(b);
            referrers(b);
            countries(b);
            clicks_by_minute(b);
            clicks_by_day(b);
            bitly_pro_domain(b);
            lookup(b);
            info(b);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}

