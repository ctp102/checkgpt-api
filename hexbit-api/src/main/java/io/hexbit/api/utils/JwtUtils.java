package io.hexbit.api.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtils {

//    private final PrivateKey privateKey;
//    private final PublicKey publicKey;
//    private final Environment environment;
//    private final Pattern backdoorPattern;
//    private final MerchantService merchantService;
//    private final UserService userService;
//
//    public GearJwtUtils(Environment environment, MerchantService merchantService, UserService userService) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
//        Security.addProvider(new BouncyCastleProvider());
//        this.privateKey      = privateKey(environment);
//        this.publicKey       = publicKey(environment);
//        this.environment     = environment;
//        this.merchantService = merchantService;
//        this.userService     = userService;
//        String prefix = GearCommonUtils.encrypterSHA256("backdoorauthtoken");
//        this.backdoorPattern = Pattern.compile(prefix + "(\\d+)");
//    }
//
//    private static final long TOKEN_DURATION = 1000L * 60 * 60 * 24 / 7;
//
//    public String createAdminJwt(MerchantAdminDomain merchantAdminDomain) {
//        long nowMillis = System.currentTimeMillis();
//        long expMillis = nowMillis + TOKEN_DURATION;
//        Date exp = new Date(expMillis);
//
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("jti", UUID.randomUUID());
//        claims.put("merchantSeq", merchantAdminDomain.getMerchantSeq());
//        claims.put("admMno", merchantAdminDomain.getAdmMno());
//        claims.put("email", merchantAdminDomain.getEmail());
//        claims.put("walletAddress", merchantAdminDomain.getWalletAddress());
//        claims.put("admRoleTypeDcd", merchantAdminDomain.getAdmRoleTypeDcd());
//        claims.put("admRoleType", merchantAdminDomain.getAdmRoleType());
//        claims.put("pwdChange", merchantAdminDomain.getPwdChange());
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(new Date(nowMillis))
//                .signWith(SignatureAlgorithm.RS256, privateKey)
//                .setExpiration(exp)
//                .compact();
//    }
//
//    public String createFrontJwt(UserDomain userDomain) {
//        long nowMillis = System.currentTimeMillis();
//        long expMillis = nowMillis + TOKEN_DURATION;
//        Date exp = new Date(expMillis);
//
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("jti", UUID.randomUUID());
//        claims.put("userSeq", userDomain.getUserSeq());
//        claims.put("walletAddress", userDomain.getWalletAddress());
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(new Date(nowMillis))
//                .signWith(SignatureAlgorithm.RS256, privateKey)
//                .setExpiration(exp)
//                .compact();
//    }
//
//    public AdminAuthToken parseAdminJwt(String encryptedToken) {
//
//        if (!environment.acceptsProfiles(Profiles.of("live"))) {
//            Matcher matcher = backdoorPattern.matcher(encryptedToken);
//            if (matcher.matches()) {
//                long merchantSeq = Long.parseLong(matcher.group(1));
//                MerchantDomain merchantDomain = merchantService.getMerchant(merchantSeq);
//
//                if (merchantDomain == null || merchantDomain.getMerchantAdminDomainList() == null || merchantDomain.getMerchantAdminDomainList().isEmpty()) {
//                    return null;
//                }
//
//                MerchantAdminDomain merchantAdminDomain = merchantDomain.getMerchantAdminDomainList().stream().findFirst().orElseThrow();
//
//                AdminAuthToken adminAuthToken = new AdminAuthToken();
//                adminAuthToken.setToken(encryptedToken);
//                adminAuthToken.setJti("backdoor-jti");
//                adminAuthToken.setMerchantSeq(merchantSeq);
//                adminAuthToken.setAdmMno(merchantAdminDomain.getAdmMno());
//                adminAuthToken.setEmail(merchantAdminDomain.getEmail());
//                adminAuthToken.setWalletAddress(merchantAdminDomain.getWalletAddress());
//                adminAuthToken.setAdmRoleTypeDcd(merchantAdminDomain.getAdmRoleTypeDcd());
//                adminAuthToken.setAdmRoleType(merchantAdminDomain.getAdmRoleType());
//                adminAuthToken.setPwdChange(merchantAdminDomain.getPwdChange());
//
//                return adminAuthToken;
//            }
//        }
//
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(publicKey)
//                    .parseClaimsJws(encryptedToken)
//                    .getBody();
//
//            AdminAuthToken adminAuthToken = new AdminAuthToken();
//            adminAuthToken.setToken(encryptedToken);
//            adminAuthToken.setJti(claims.get("jti").toString());
//            adminAuthToken.setMerchantSeq(Long.parseLong(claims.get("merchantSeq").toString()));
//            adminAuthToken.setAdmMno(Long.parseLong(claims.get("admMno").toString()));
//            adminAuthToken.setEmail(claims.get("email").toString());
//            adminAuthToken.setWalletAddress(claims.get("walletAddress").toString());
//            adminAuthToken.setAdmRoleTypeDcd(claims.get("admRoleTypeDcd").toString());
//            adminAuthToken.setAdmRoleType(claims.get("admRoleType").toString());
//            adminAuthToken.setPwdChange(claims.get("pwdChange").toString());
//
//            return adminAuthToken;
//        } catch (ExpiredJwtException e) {
//            log.error("[JWT] AuthToken ExpiredJwtException :: token = [" + encryptedToken + "], Error = ", e);
//        } catch (Exception e) {
//            log.error("Failed to decrypt JWT token", e);
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    public FrontAuthToken parseFrontJwt(String encryptedToken) {
//
//        if (!environment.acceptsProfiles(Profiles.of("live"))) {
//            Matcher matcher = backdoorPattern.matcher(encryptedToken);
//            if (matcher.matches()) {
//                long userSeq = Long.parseLong(matcher.group(1));
//                UserDomain userDomain = userService.getUser(userSeq);
//
//                FrontAuthToken frontAuthToken = new FrontAuthToken();
//                frontAuthToken.setToken(encryptedToken);
//                frontAuthToken.setJti("backdoor-jti");
//                frontAuthToken.setUserSeq(userSeq);
//                frontAuthToken.setWalletAddress(userDomain.getWalletAddress());
//
//                return frontAuthToken;
//            }
//        }
//
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(publicKey)
//                    .parseClaimsJws(encryptedToken)
//                    .getBody();
//
//            FrontAuthToken frontAuthToken = new FrontAuthToken();
//            frontAuthToken.setToken(encryptedToken);
//            frontAuthToken.setJti(claims.get("jti").toString());
//            frontAuthToken.setUserSeq(Long.parseLong(claims.get("userSeq").toString()));
//            frontAuthToken.setWalletAddress(claims.get("walletAddress").toString());
//
//            return frontAuthToken;
//        } catch (ExpiredJwtException e) {
//            log.error("[JWT] AuthToken ExpiredJwtException :: token = [" + encryptedToken + "], Error = ", e);
//        } catch (Exception e) {
//            log.error("Failed to decrypt JWT token", e);
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    public PrivateKey privateKey(Environment environment) throws IOException {
//        String keyPath = environment.acceptsProfiles(Profiles.of("live")) ? "jwtKey/live/private.pem" : "jwtKey/dev/private.pem";
//        try (InputStreamReader isr = new InputStreamReader(new ClassPathResource(keyPath).getInputStream());
//             BufferedReader br = new BufferedReader(isr)) {
//            String pem = br.lines().collect(Collectors.joining("\n"));
//
//            PEMParser pemParser = new PEMParser(new StringReader(pem));
//            Object object = pemParser.readObject();
//
//            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
//            PrivateKeyInfo privateKeyInfo = ((PEMKeyPair) object).getPrivateKeyInfo();
//
//            return converter.getPrivateKey(privateKeyInfo);
//        }
//    }
//
//    public PublicKey publicKey(Environment environment) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
//        String keyPath = environment.acceptsProfiles(Profiles.of("live")) ? "jwtKey/live/public.pem" : "jwtKey/dev/public.pem";
//        try (InputStreamReader isr = new InputStreamReader(new ClassPathResource(keyPath).getInputStream());
//             BufferedReader br = new BufferedReader(isr)) {
//            String pem = br.lines().collect(Collectors.joining("\n"));
//
//            PEMParser pemParser = new PEMParser(new StringReader(pem));
//            Object object = pemParser.readObject();
//
//            SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(object);
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyInfo.getEncoded());
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            return keyFactory.generatePublic(keySpec);
//        }
//    }

}
