package com.tvd12.ezyfox.testing.security;

import com.tvd12.ezyfox.security.EzyAsyCrypt;
import com.tvd12.ezyfox.security.EzyBase64;
import com.tvd12.test.base.BaseTest;

public class EzyAsyCryptEncryptDecryptTest extends BaseTest {

    public static void main(String[] args) throws Exception {
        String publicKey =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwmI6UDOjfjs/aDdn7hm2CvFs/Wc1E4C4GAzy20/ggLKZT+rf1EBe7034osMaFHne/FVQ56UNbphIYZtll6aaB+Z/r+Sm0oyOmr7S0wMftwBNaCnJN7rR4AlXIEJwfv9LZGhmt0VFvXdYm/0uRpmUaX+oU8mCCD/yxZp1w/m1x4TeUFMSpBMtWq+KcodbqBmNu7nLxWrbDkygxK1970dPADurT5MSCxdFR2sdkDNRLSYtAmXq6rMIsyhL8U+N0510zizVTlzcgB5p6vfDmUYocKy5l3LAYrk2JwPZ+c0Ik1jYcL8fU8sCSCwoXgY0fnXmfTDCoJ+xwd1nz7foiPLDwwIDAQAB";
        String privateKey =
            "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCD+eQdACBwhVvLN82bv0hKBz34bfcX226jMUpJ21SvFevK/yqXDg/F85sxpfAZG0cKKAdywx8byqakzVMycZilzNGMLX+nPGkZ4pfM4fKEJuNesp0bsUtWDYdtvXTFB/TtDqIjWvVr4GvwsPiKuky6FmePDv3p1sog3taEZ+BEh8Wk8AnfIgHEhk4afNcF1GGZNQVHxxEBU+7laV8djkp0+v28XHafN58DSjhFuNUUMOEsWRqDS932BhOFUT73ie/GRJ7XSXf79G5duKUkbsnBgktMeVLm4wbCc4tGxqoGTEgtPKYGrEw7ZnZqAYBmluKvAcMzbuGYhe2TYO9lkPmDAgMBAAECggEAArqI79yPzyPCuRnnQ2AaE4XAz0Eeybx8th0PxyUkxBX08RaDf+oHg3U88TLYGvzIZDpdpQ7Jtt5iwaeJH9LfcBYjAv3x1LhYB/P6ZX8Kb5cKPmv8+ZSZ1SakdS1km6pFcx1JVufLiTZzpN9T1DJz4akS7Q9HSq1pMnLJBqXLKj+Iv2aSsY3m2b0TJvdNnnqJcAVD6TZbONeh6WsEJGUH2Kn/3igttjZg7xGWgBkBL53bWT00Q7llseBdLyB3hyV70DTtSzWXjsiTsZdWkQxET/biCCWDTxtNJkbmcUsyTCK5QGVGfnEM5HeAjTMhM8bpONfpzTp3sXy5A2r34T0fyQKBgQDL0SB5Ijr2jiKkI2cl9PL3CxLHOi6FKLUiKoNcwMkQZJ7A0F6Kjk4Pb2fv5OyaW2117nxHIBJgMjn0N6TxctJ8lHytf96i62uJWDh6ssiXbFVw5uro0pbSicNYJDkg7zdreJBZexnZgOq319y/Mamruv32dFu5B5AakC1/0ala5QKBgQClxBGsTDAVSy+KN5za3FQczLnG7+IlmPp6KH3NksWPI9ll49vwhsmao9gpc/kkHye/TB3pUSa1Qi4qW4fnkcAl+wpZ+550QvQd241cb/r1Q8G7B13F+Jk9Lkmp72vLV4Cq/qJOfOvwFIdEAv60MC6Vp0ijJ7DuAaw8aKjk3TV0RwKBgGBIuv5+nTw8iUDhOKlFvkHWiVh0s+VUBb3ON6BpspJ79kNALGloMJg6GO/LRbrl1iAQOcWf8Q5gq+AjKVim1ajkvMF51sNAU9zeQFA2hWyDURm0/ORgF7/+NLo+4n4chCHRIuAit2N482lGIJJx86WdALyc23fdisYBtnzbnxXBAoGANfGDSs2UTCmmc9U7LyHKIYEh57JCpreMs1of7lkX/0fMRFMNTM2gJ7mv63V5Gx8kYSVVCotFDHFX8qLMrDgLDpEOQlAWtYDgTiMXcoBia5Q8JC/2fSk0POumPy3Rp193WK36hY4zfXJAvoo29s5DKqJAig+tjRZwRUxNwgSWk5sCgYAp04lG1BYijnh7jGzEXtjO//ItmV7us48atzfbwH/DiT85oNmcE4PdZpeNr3Whn/tpCmBCvX5GnQrEI9p63XWSmkOiCcd7UrukP7WHzJ8VWyGyUSbK1Zke+EoPpAoQ9RfHXpkQkyT2y4pUWTjleqTLggRPlsxFsIuOcl/F2SVghA==";
        EzyAsyCrypt asyCrypt = EzyAsyCrypt.builder()
            .publicKey(EzyBase64.decode(publicKey))
            .privateKey(EzyBase64.decode(privateKey))
            .build();
        byte[] encryptedBytes = asyCrypt.encrypt("Hello");
        System.out.println(encryptedBytes.length);
        String encryptedText = EzyBase64.encode2utf(encryptedBytes);
        System.out.println(encryptedText);
        String decryptedText = new String(
            asyCrypt.decrypt(EzyBase64.decode(encryptedText))
        );
        System.out.println(decryptedText);
    }
}
