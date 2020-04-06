package com.backend.bank.common;

import com.backend.bank.exception.BadRequestException;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class Constants {
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 30 * 24 * 60 * 60;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "scopes";
    public static final int SUCCESS = 1;
    public static final int YEAR = 5;
    public static final int ACCEPT = 1;
    public static final int DENY = 0;
    public static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    public static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    public static final Pattern EDGESDHASHES = Pattern.compile("(^-|-$)");
    public static final String VI = "vi";
    public static final String EN = "en";
    public static final String TypeRegulation = "Quy chế";
    public static final String TypeTutorial = "Hướng dẫn";

    public static final int ALLOW = 1;
    public static final int INHERIT = 2;

    public static final String TOP = "top2";

    public static final String BOTTOM = "bottom";
    public static final String SIDE = "side";
    public static final String MIDDLE = "middle";
    public static final String TOP_TOP = "top_top";
    public static final String FOOTER_TOP = "Menu footer top";
    public static final String FOOTER_MAIN = "Menu footer main";
    public static final String FOOTER_BOTTOM = "menu footer bottom";

    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    public static String checkPositionOfMenu(String position) {
        if (!position.equals(Constants.MIDDLE)
                && !position.equals(Constants.TOP)
                && !position.equals(Constants.TOP_TOP)
                && !position.equals(Constants.FOOTER_TOP)
                && !position.equals(Constants.FOOTER_MAIN)
                && !position.equals(Constants.FOOTER_BOTTOM)
                && !position.equals(Constants.BOTTOM)
                && !position.equals(Constants.SIDE)
                && !position.equals("")) {
            throw new BadRequestException("Position in valid");
        }
        return position;
    }

    public class Message {
        public static final String SUCCESSFULLY = "Successfully";
    }

    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        slug = EDGESDHASHES.matcher(slug).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    public static String checkLocal(String local) {
        if (!local.equals(Constants.VI) && !local.equals(Constants.EN)) {
            throw new BadRequestException("Local in valid");
        }
        return local;
    }


    public static String checkType(String local) {
        if (!local.equals(Constants.TypeRegulation) && !local.equals(Constants.TypeTutorial)) {
            throw new BadRequestException("Type in valid");
        }
        return local;
    }
}