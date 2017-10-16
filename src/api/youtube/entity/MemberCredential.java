package api.youtube.entity;

import api.youtube.utility.Utils;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * A simple token for student. Save token key and userId.
 *
 * Created by daolinh on 10/9/17.
 */
@Entity
public class MemberCredential {

    @Id
    private String token;
    @Unindex
    private String secretToken;
    @Index
    private long userId;
    @Index
    private long createdTimeMLS;
    @Index
    private long expiredTimeMLS;
    @Index
    private int status;

    public MemberCredential() {

    }

    public MemberCredential(long userId) {
        this.userId = userId;
        this.token = Utils.generateToken();
        this.secretToken = this.token;
        this.createdTimeMLS = System.currentTimeMillis();
        // default expire date +7.
        this.expiredTimeMLS = Utils.addDays(7);
        this.status = 1;
    }

    public String getSecretToken() {
        return secretToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        this.secretToken = this.token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCreatedTimeMLS() {
        return createdTimeMLS;
    }

    public void setCreatedTimeMLS(long createdTimeMLS) {
        this.createdTimeMLS = createdTimeMLS;
    }

    public long getExpiredTimeMLS() {
        return expiredTimeMLS;
    }

    public void setExpiredTimeMLS(long expiredTimeMLS) {
        this.expiredTimeMLS = expiredTimeMLS;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static MemberCredential loadCredential(String  secrectToken){
        if (secrectToken == null){
            return null;
        }
        MemberCredential credential = ofy().load().type(MemberCredential.class).id(secrectToken).now();
        if(credential == null){
            return null;
        }
        if(credential.getExpiredTimeMLS() < System.currentTimeMillis()){
            return null;
        }
        return credential;
    }
}
