package com.dd.sdk.net.rgw;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.net.rgw
 * @class describe
 * @time 2018/6/19 16:57
 * @change
 * @class describe
 */

public class RgwAdminBuilder {
    private String accessKey;
    private String secretKey;
    private String endpoint;

    /**
     * Sets the access key to be used by the client.
     *
     * <p>Note that the corresponding user, i.g, the administrator, should has proper administrative
     * capabilities. See more about administrative capabilities <a
     * href="http://docs.ceph.com/docs/master/radosgw/admin/#add-remove-admin-capabilities">here</a>
     *
     * @param accessKey Access key to use.
     * @return This object for method chaining.
     */
    public RgwAdminBuilder accessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    /**
     * Sets the secret key to be used by the client.
     *
     * <p>Note that the corresponding user, i.g, the administrator, should has proper administrative
     * capabilities. See more about administrative capabilities <a
     * href="http://docs.ceph.com/docs/master/radosgw/admin/#add-remove-admin-capabilities">here</a>
     *
     * @param secretKey Secret key to use.
     * @return This object for method chaining.
     */
    public RgwAdminBuilder secretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    /**
     * Sets the endpoint to be used for requests.
     *
     * <p>For example: http://127.0.0.1:80/admin
     *
     * <p>Note that the admin API in the corresponding Radosgw should be enabled.
     *
     * @param endpoint Endpoint to use.
     * @return This object for method chaining.
     */
    public RgwAdminBuilder endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    /**
     * Builds a client with the configure properties.
     *
     * @return Client instance to make API calls with.
     */
    public RgwAdmin build() {
       /* if (Arrays.asList(accessKey, secretKey, endpoint)) {
            throw new IllegalArgumentException("Missing required parameter to build the instance.");
        }*/
        return new RgwAdminImpl(accessKey, secretKey, endpoint);
    }
}
