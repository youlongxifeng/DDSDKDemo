package com.dd.sdk.net.rgw;

import java.util.Map;
import java.util.Optional;

/**
 * Radosgw administrator
 * <p>
 * <p>Administer the Ceph Object Storage (a.k.a. Radosgw) service with user management, access
 * controls, quotas and usage tracking among other features.
 * <p>
 * <p>Note that to some operations needs proper configurations on radosgw, and require that the
 * requester holds special administrative capabilities.
 * <p>
 * <p>Created by petertc on 3/14/17.
 */
@SuppressWarnings("SameParameterValue")
public interface RgwAdmin {


    /**
     * Read the policy of an object.
     * <p>
     * <p>Note that the term "policy" here does not stand for "S3 bucket policy". Instead, it represents
     * S3 Access Control Policy (ACP).
     * <p>
     * <p>We return JSON string instead of the concrete model here due to the server returns the
     * internal data structure which is not well defined. For example:
     * <p>
     * <pre>
     * {
     *    "acl":{
     *       "acl_user_map":[
     *          {
     *             "user":"rgwAdmin4jTest-6d6a2645-0219-4e49-8493-0bdc8cb00e19",
     *             "acl":15
     *          }
     *       ],
     *       "acl_group_map":[
     *       ],
     *       "grant_map":[
     *          {
     *             "id":"rgwAdmin4jTest-6d6a2645-0219-4e49-8493-0bdc8cb00e19",
     *             "grant":{
     *                "type":{
     *                   "type":0
     *                },
     *                "id":"rgwAdmin4jTest-6d6a2645-0219-4e49-8493-0bdc8cb00e19",
     *                "email":"",
     *                "permission":{
     *                   "flags":15
     *                },
     *                "name":"rgwAdmin4jTest-6d6a2645-0219-4e49-8493-0bdc8cb00e19",
     *                "group":0,
     *                "url_spec":""
     *             }
     *          }
     *       ]
     *    },
     *    "owner":{
     *       "id":"rgwAdmin4jTest-6d6a2645-0219-4e49-8493-0bdc8cb00e19",
     *       "display_name":"rgwAdmin4jTest-6d6a2645-0219-4e49-8493-0bdc8cb00e19"
     *    }
     * }
     * </pre>
     *
     * @param bucketName The bucket to which the object belong to.
     * @param objectKey  The object to read the policy from.
     * @return If successful returns the policy.
     */
    Optional<String> getObjectPolicy(String bucketName, String objectKey);

    /**
     * Read the policy of a bucket.
     * <p>
     * <p>Note that the term "policy" here does not stand for "S3 bucket policy". Instead, it represents
     * S3 Access Control Policy (ACP).
     * <p>
     * <p>We return JSON string instead of the concrete model here due to the server returns the
     * internal data structure which is not well defined. For example:
     * <p>
     * <pre>
     * {
     *    "acl":{
     *       "acl_user_map":[
     *          {
     *             "user":"rgwAdmin4jTest-6d6a2645-0219-4e49-8493-0bdc8cb00e19",
     *             "acl":15
     *          }
     *       ],
     *       "acl_group_map":[
     *       ],
     *       "grant_map":[
     *          {
     *             "id":"rgwAdmin4jTest-6d6a2645-0219-4e49-8493-0bdc8cb00e19",
     *             "grant":{
     *                "type":{
     *                   "type":0
     *                },
     *                "id":"rgwAdmin4jTest-6d6a2645-0219-4e49-8493-0bdc8cb00e19",
     *                "email":"",
     *                "permission":{
     *                   "flags":15
     *                },
     *                "name":"rgwAdmin4jTest-6d6a2645-0219-4e49-8493-0bdc8cb00e19",
     *                "group":0,
     *                "url_spec":""
     *             }
     *          }
     *       ]
     *    },
     *    "owner":{
     *       "id":"rgwAdmin4jTest-6d6a2645-0219-4e49-8493-0bdc8cb00e19",
     *       "display_name":"rgwAdmin4jTest-6d6a2645-0219-4e49-8493-0bdc8cb00e19"
     *    }
     * }
     * </pre>
     *
     * @param bucketName The bucket to read the policy from.
     * @return If successful returns the policy.
     */
    Optional<String> getBucketPolicy(String bucketName);

    void createBucket(String bucketName, Map<String, String> parameters);

    void getBucket();

    void deleteBucket(String bucketName);

    void putBucketObject(String bucketName, String objectName, String filePath, Map<String, String> parameters);
}
