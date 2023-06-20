package com.example;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GiftPesaAPI {
    private static final String BASE_URL = "https://3api.giftpesa.com";
    private static final String AUTH_ENDPOINT = "/authorize";
    private static final String MERCHANTS_ENDPOINT = "/merchants";
    private static final String DISBURSE_ENDPOINT = "/disburse";
    private static final String QUERY_ENDPOINT = "/query/";
    private static final String REDEEM_ENDPOINT = "/redeem";

    private static final String API_KEY = "";
    private static final String USERNAME = "+254797495525";
    private static final String ACCESS_TOKEN = "";

    public static void main(String[] args) {
        GiftPesaAPI giftPesaAPI = new GiftPesaAPI();
        System.out.println(USERNAME);
        String authorizationToken = giftPesaAPI.generateAuthorizationToken();
        System.out.println(authorizationToken);

        giftPesaAPI.getMerchants(authorizationToken);
        giftPesaAPI.createDisbursement(authorizationToken);
        String disbursementId = "<Your Disbursement ID>";
        giftPesaAPI.queryDisbursement(authorizationToken, disbursementId);
        giftPesaAPI.redeemVoucher(authorizationToken);
    }

    public String generateAuthorizationToken() {
        try {
            URL url = new URL(BASE_URL + AUTH_ENDPOINT);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Basic " + encodeBase64(API_KEY));
            conn.setRequestProperty("Username", USERNAME);

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            } else {
                System.out.println("Authorization failed. Response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getMerchants(String authorizationToken) {
        try {
            URL url = new URL(BASE_URL + MERCHANTS_ENDPOINT);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + authorizationToken);

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println(response.toString());
            } else {
                System.out.println("Failed to fetch merchants. Response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createDisbursement(String authorizationToken) {
        try {
            URL url = new URL(BASE_URL + DISBURSE_ENDPOINT);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + authorizationToken);
            conn.setRequestProperty("Content-Type", "application/json");
            
            // Set the request payload
            String payload = "{\"DisbursementID\":\"<DisbursementID>\",\"DisbursementTitle\":\"XYZ Disbursement\",\"VoucherStartDate\":\"20210512\",\"VoucherEndDate\":\"20210820\",\"Merchant\":[\"naivas\",\"quickmart\"],\"Recipients\":[{\"Name\":\"Jane Doe\",\"Phone\":\"07XXXXXXXX\",\"Amount\":100},{\"Name\":\"John Doe\",\"Phone\":\"07XXXXXXXX\",\"Amount\":50}],\"External\":true,\"CallbackURL\":\"https://test2.com\",\"SuppressNotifications\":true}";
            conn.setDoOutput(true);
            conn.getOutputStream().write(payload.getBytes("UTF-8"));

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println(response.toString());
            } else {
                System.out.println("Failed to create disbursement. Response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void queryDisbursement(String authorizationToken, String disbursementId) {
        try {
            URL url = new URL(BASE_URL + QUERY_ENDPOINT + disbursementId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + authorizationToken);

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println(response.toString());
            } else {
                System.out.println("Failed to query disbursement. Response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void redeemVoucher(String authorizationToken) {
        try {
            URL url = new URL(BASE_URL + REDEEM_ENDPOINT);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + authorizationToken);
            conn.setRequestProperty("Content-Type", "application/json");
            
            // Set the request payload
            String payload = "{\"TransactionID\":\"<TransactionID>\",\"Name\":\"Jane Doe\",\"Phone\":\"+254720XXXXXX\",\"TillNo\":\"123456\",\"Amount\":10,\"Remarks\":\"Weekly shopping\"}";
            conn.setDoOutput(true);
            conn.getOutputStream().write(payload.getBytes("UTF-8"));

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println(response.toString());
            } else {
                System.out.println("Failed to redeem voucher. Response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String encodeBase64(String value) {
        return java.util.Base64.getEncoder().encodeToString(value.getBytes());
    }
}
