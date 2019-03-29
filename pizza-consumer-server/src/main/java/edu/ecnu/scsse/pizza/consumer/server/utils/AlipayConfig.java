package edu.ecnu.scsse.pizza.consumer.server.utils;

public class AlipayConfig {
    public static final String GATE_WAY = "https://openapi.alipaydev.com/gateway.do";
    public static final String PRIVATE_KEY = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQCcaTw6Kwe1a2uKOrcNpd5g8tc4Wxhj/aCO/3cRqnASBzsaqC8z1NCh7qXt0o/jKMAKJuFgpFy9yk1s0H4BT0OikNz3xM4BFICfQm/2IahEOy5OnM2OcNnxTOfC5wlN6hiwAe3RDju21vVIq+dSMXZKVDFXwcSKHQcD5F6URWXW2KwbMmMakzDkPHzsTdnuKfQbyA9QJKtCoGf3WBOM/E4zkND6Wbs0u5ZP6snrPtFNqsuodrKptrxUL2dsAtDZC5EMHkGPqQHsRAOyXlvkl/Iqzk8wxwQzk2TWnWNvaXHQFKdlZDXmPQfTMnZVmj6CXg3s7QxQKGCP3FXQgrEw57GpAgMBAAECggEBAJPt179v+Q91dDU9COoIiUUKB9FOHshbFSKJrPRxTSNP4W95x4Lu8Q9mJVrDfcCrRs2TLi935BrAHfAfVD0q2V4EQmi3otpUqL13WWrhNEKr8kT5elQQmZ/lS4EDf8JH+Gdmu6SwIHvx2/SKWPJcw3LTJAPKyHaTs5HTyDvVUtcZge+aj8VQhc/eT1bFjUh+vYxkoHLk4TLf/KfrF7DU/BaPsBCIofkwBJEn9ZsfEYULeatrohPvIF4qXILfqS/1jfdpDDjEY32VeU/AIPJxZGlTdY9+WQTbmIXqd+uEspu7wjNG93Qh3eMAkpp5H829QiDtbZdSVfUZKQP5Cup34LECgYEA5LklY2NW6JIpSSUT74y0gAO2rHIrwbZqpFLJpOPIEgfNPcY/ne0DGC6GFL/cyCCNBXlMfE5XzOEzbIW3Sz/cL6T50JN+IpWsC1NPB0oSObUJFk2Nv/d4Q7rAbVap6O5kkO6mWXOCDnbPwMAeJwk+EnTD5iUsCCt22H6Z8b1wNoUCgYEArxBrjrX5fSw2md4HgbSi1vYa1z6lThOw6DMD2r5ZAqbrC16oBlVtwRaEOMoKWpspEkMmRjO3XoMgSAvEbIaVeW2fXWV78T4dQmIJMb4ea+3jdanE9HF7T5/xE23dO/OMnkBI5E/EZzUQhDt+Yw01JVPsrE71st2jDrOCRDvVkdUCgYEAoCp64Rdec6xultcNF9HbUE3JbQbiV1trYDjNFdegcefetKSQRgIECboCkKMuc0JAHpYrllyyJTmKbjNRALPud7q6aXHvgT54ZNHo5HzdOGXqwCB84/Hi2OL8/1QR1Zii+c701G0LpR9UKnEebE2PlxMHX7GmHO564e391YfH/ZUCgYEAgIaqoKzDTX3PXGng0v4+a1ll3U8157uXLvJ9SvRivwqzF7bX30aK/K6+Rj83GMw/fBPmnrXLU00rvxk8jXzFcs/jp5shQr0amCkHnQh6pzyQuUr5uYkzQgMn597KBQDK5UhG+7AAftE7nglbwfbpZM1Xvi1+P2JvGxT5d4UCSD0CgYEA1dKpHQFOnQxjfHxSVektYUiHIb6Jwo8a+4xOoEr7OlRrnYLBP5hTfEOrl+XrNXSCFQ8X4X+sxHAHwirzqhrxvfRoQwQTlQ04+vRHoUKr4USsck9GZ/u85BhhJMgd3eZne/zpnr3aGY1zcvJ6UlHt0k7T44pi85xPf64VPZ0fnAk=";
    public static final String APP_ID = "2016080900201394";

    // 编码
    public static final String CHARSET = "UTF-8";
    // 返回格式
    public static final String FORMAT = "json";
    // 支付宝公钥
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvpVRHimrRiVooq7vX7+cS2fOx2oxryfdAsbixfKgebWApOR4dCZxuUlzZqIBRPueP98JTH3aEZ0jNgYDqqBQr/FJC7qgKCyczLVZ5qG6IA4+iNOTfrgzIc5mhA5/t3serIv1ehegsKtbCuEbm7ls/brINYS2cRRE7OlQmN0HxelvKfSMk8Ip4+06ynrXfWcKh4DfoknlA9xVhNTLNZ645CF1OEhz77qbso9CGVyUFva6t/2uRR0Vu1v1YZo4BiIH65v5AkGQvN4Y428blyMxKdbHxEpORAb1GmA7mtrORsQ5/+lqLmQuGBrIU+geLQ/q91aVMvKzSaZnXH946yq1bQIDAQAB";
    // RSA2
    public static final String SIGNTYPE = "RSA2";

    public static final String SUBJECT = "pizza_order";

    public static final String PRODUCT_CODE = "QUICK_WAP_WAY";

    public static final String NOTIFY_URL = "http://www.baidu.com";

    public static final String RETURN_URL = "http://www.baidu.com";
}
