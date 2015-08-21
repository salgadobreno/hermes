package com.avixy.qrtoken.gui.components;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

import java.util.*;

/**
 * Created on 23/03/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TimeZoneField extends ComboBox<String> {
    private static final String[] NAMES = { "International Date Line West" , "Midway Island" , "American Samoa" , "Hawaii" , "Alaska" , "Pacific Time (US & Canada)" , "Tijuana" , "Mountain Time (US & Canada)" , "Arizona" , "Chihuahua" , "Mazatlan" , "Central Time (US & Canada)" , "Saskatchewan" , "Guadalajara" , "Mexico City" , "Monterrey" , "Central America" , "Eastern Time (US & Canada)" , "Indiana (East)" , "Bogota" , "Lima" , "Quito" , "Atlantic Time (Canada)" , "Caracas" , "La Paz" , "Santiago" , "Newfoundland" , "Brasilia" , "Buenos Aires" , "Montevideo" , "Georgetown" , "Greenland" , "Mid-Atlantic" , "Azores" , "Cape Verde Is." , "Dublin" , "Edinburgh" , "Lisbon" , "London" , "Casablanca" , "Monrovia" , "UTC" , "Belgrade" , "Bratislava" , "Budapest" , "Ljubljana" , "Prague" , "Sarajevo" , "Skopje" , "Warsaw" , "Zagreb" , "Brussels" , "Copenhagen" , "Madrid" , "Paris" , "Amsterdam" , "Berlin" , "Bern" , "Rome" , "Stockholm" , "Vienna" , "West Central Africa" , "Bucharest" , "Cairo" , "Helsinki" , "Kyiv" , "Riga" , "Sofia" , "Tallinn" , "Vilnius" , "Athens" , "Istanbul" , "Minsk" , "Jerusalem" , "Harare" , "Pretoria" , "Kaliningrad" , "Moscow" , "St. Petersburg" , "Volgograd" , "Samara" , "Kuwait" , "Riyadh" , "Nairobi" , "Baghdad" , "Tehran" , "Abu Dhabi" , "Muscat" , "Baku" , "Tbilisi" , "Yerevan" , "Kabul" , "Ekaterinburg" , "Islamabad" , "Karachi" , "Tashkent" , "Chennai" , "Kolkata" , "Mumbai" , "New Delhi" , "Kathmandu" , "Astana" , "Dhaka" , "Sri Jayawardenepura" , "Almaty" , "Novosibirsk" , "Rangoon" , "Bangkok" , "Hanoi" , "Jakarta" , "Krasnoyarsk" , "Beijing" , "Chongqing" , "Hong Kong" , "Urumqi" , "Kuala Lumpur" , "Singapore" , "Taipei" , "Perth" , "Irkutsk" , "Ulaanbaatar" , "Seoul" , "Osaka" , "Sapporo" , "Tokyo" , "Yakutsk" , "Darwin" , "Adelaide" , "Canberra" , "Melbourne" , "Sydney" , "Brisbane" , "Hobart" , "Vladivostok" , "Guam" , "Port Moresby" , "Magadan" , "Srednekolymsk" , "Solomon Is." , "New Caledonia" , "Fiji" , "Kamchatka" , "Marshall Is." , "Auckland" , "Wellington" , "Nuku'alofa" , "Tokelau Is." , "Chatham Is." , "Samoa" };
    private static final String[] VALUES = { "Pacific/Midway" , "Pacific/Midway" , "Pacific/Pago_Pago" , "Pacific/Honolulu" , "America/Juneau" , "America/Los_Angeles" , "America/Tijuana" , "America/Denver" , "America/Phoenix" , "America/Chihuahua" , "America/Mazatlan" , "America/Chicago" , "America/Regina" , "America/Mexico_City" , "America/Mexico_City" , "America/Monterrey" , "America/Guatemala" , "America/New_York" , "America/Indiana/Indianapolis" , "America/Bogota" , "America/Lima" , "America/Lima" , "America/Halifax" , "America/Caracas" , "America/La_Paz" , "America/Santiago" , "America/St_Johns" , "America/Sao_Paulo" , "America/Argentina/Buenos_Aires" , "America/Montevideo" , "America/Guyana" , "America/Godthab" , "Atlantic/South_Georgia" , "Atlantic/Azores" , "Atlantic/Cape_Verde" , "Europe/Dublin" , "Europe/London" , "Europe/Lisbon" , "Europe/London" , "Africa/Casablanca" , "Africa/Monrovia" , "Etc/UTC" , "Europe/Belgrade" , "Europe/Bratislava" , "Europe/Budapest" , "Europe/Ljubljana" , "Europe/Prague" , "Europe/Sarajevo" , "Europe/Skopje" , "Europe/Warsaw" , "Europe/Zagreb" , "Europe/Brussels" , "Europe/Copenhagen" , "Europe/Madrid" , "Europe/Paris" , "Europe/Amsterdam" , "Europe/Berlin" , "Europe/Berlin" , "Europe/Rome" , "Europe/Stockholm" , "Europe/Vienna" , "Africa/Algiers" , "Europe/Bucharest" , "Africa/Cairo" , "Europe/Helsinki" , "Europe/Kiev" , "Europe/Riga" , "Europe/Sofia" , "Europe/Tallinn" , "Europe/Vilnius" , "Europe/Athens" , "Europe/Istanbul" , "Europe/Minsk" , "Asia/Jerusalem" , "Africa/Harare" , "Africa/Johannesburg" , "Europe/Kaliningrad" , "Europe/Moscow" , "Europe/Moscow" , "Europe/Volgograd" , "Europe/Samara" , "Asia/Kuwait" , "Asia/Riyadh" , "Africa/Nairobi" , "Asia/Baghdad" , "Asia/Tehran" , "Asia/Muscat" , "Asia/Muscat" , "Asia/Baku" , "Asia/Tbilisi" , "Asia/Yerevan" , "Asia/Kabul" , "Asia/Yekaterinburg" , "Asia/Karachi" , "Asia/Karachi" , "Asia/Tashkent" , "Asia/Kolkata" , "Asia/Kolkata" , "Asia/Kolkata" , "Asia/Kolkata" , "Asia/Kathmandu" , "Asia/Dhaka" , "Asia/Dhaka" , "Asia/Colombo" , "Asia/Almaty" , "Asia/Novosibirsk" , "Asia/Rangoon" , "Asia/Bangkok" , "Asia/Bangkok" , "Asia/Jakarta" , "Asia/Krasnoyarsk" , "Asia/Shanghai" , "Asia/Chongqing" , "Asia/Hong_Kong" , "Asia/Urumqi" , "Asia/Kuala_Lumpur" , "Asia/Singapore" , "Asia/Taipei" , "Australia/Perth" , "Asia/Irkutsk" , "Asia/Ulaanbaatar" , "Asia/Seoul" , "Asia/Tokyo" , "Asia/Tokyo" , "Asia/Tokyo" , "Asia/Yakutsk" , "Australia/Darwin" , "Australia/Adelaide" , "Australia/Melbourne" , "Australia/Melbourne" , "Australia/Sydney" , "Australia/Brisbane" , "Australia/Hobart" , "Asia/Vladivostok" , "Pacific/Guam" , "Pacific/Port_Moresby" , "Asia/Magadan" , "Asia/Srednekolymsk" , "Pacific/Guadalcanal" , "Pacific/Noumea" , "Pacific/Fiji" , "Asia/Kamchatka" , "Pacific/Majuro" , "Pacific/Auckland" , "Pacific/Auckland" , "Pacific/Tongatapu" , "Pacific/Fakaofo" , "Pacific/Chatham" , "Pacific/Apia" };
    private static final Map<String, TimeZone> TIME_ZONE_MAP = new HashMap<>();

    static {
        for (int i = 0; i < NAMES.length; i++) {
            TIME_ZONE_MAP.put(NAMES[i], TimeZone.getTimeZone(VALUES[i]));
        }
    }

    public TimeZoneField() {
        ArrayList<String> timeZones = new ArrayList<>(TIME_ZONE_MAP.keySet());
        Collections.sort(timeZones);
        setItems(FXCollections.observableList(timeZones));
        getSelectionModel().select("Brasilia");
    }

    public TimeZone getTimeZone() {
        return TIME_ZONE_MAP.get(getValue());
    }
}
