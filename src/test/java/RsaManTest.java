import com.wzh.config.utils.RsaUtils;

/**
 * <一句话功能描述>
 * <功能详细描述>
 * @author wzh
 * @version 2018-12-16 23:31
 * @see [相关类/方法] (可选)
 **/
public class RsaManTest {

    public static void main(String[] args) {
        String msg = RsaUtils.decryptDataByPrivate("XsM6CYaNhdx2pJXebCgl3g3pF7FX9KrPY+gtwgbQs0Q1mqJL4VHqQytxOJfUwXHLP/hLck80AWSctJ29/dB4IQ2mSbcO4OInAJMkPwqWsnh1E9bFlFP2KjQ5RBVngb//IiSgBSFo8NR00y1/h47CrNch6ljW1nCLG82Qk2olhfI=",
                "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIlXVcewyLHiE9aidGpcR7hMT5opgXTGUfWX2idJnr2b++Edzg8Id6FOFgmzAWBkoSfAZodkTgZcOUjc/F/ZDQbuSDSBiakVb6T+WK53oVKb8a9Y9Ouq63Bwqihq9kqp9+aLSRcRqyLkF/tGg87dGMEMGeHq2djOI8rrbXGlyQNhAgMBAAECgYAHMCD3MJNwa+qp4xrArk+6n5PS97UkzXRgrC/ounuqZM2L/KlaNBE+yf1xSIMb7mhY0kMLdv52asE8xQQYaB28WVJHxExgcMDDdhtOp+4++WEe2xPWrJSUWSvLQWxrJ01yw9smezt1N0qrA4psJ+eq3JP366wZ3GLFhq0BOW8wGQJBANZyo2Dm1aP4aBHfLSa51YQJ+izFJyC53Hn43CPhXNDT08GO6tuZ9KiIJkk7rNdDrkAFnR4cDwEZT0C6Fk/VWA8CQQCj8+/fGGV+z3D0VCFkPf+VlZdNuITFD+wzlaalJZq5mYtzdHAZ3AGxpNs+Qbykq4TSb5XhfQoZuBeMD3brCv2PAkEA1Wuby4mP3zMOJ5MrtVnG9DSVxU6kxT4T/VO9ivvzSmU2XnDkrY7H3Z46NDHurwHNfivYFSopiJdut2U7ZVJW4wJAc/FsLq7IB9eXH5HnU0Zs2lHBgBr++YT7Gre3844WTy6AaZNsOz1UjVXyHaLLTwBkm5SBv8Z3QBzpugitpiZNjQJAR9SqcarmoE7xNxOJ0gqj2Pht26rAfcQogpoVvbvJTgGlpvEnnV2wEX47mzLFewzQ2nxjwAJPdzr+rOKXKnDCFA==");
        System.out.println(msg);
    }
}
