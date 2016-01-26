package fb.sio.ecp.fr.federatedbirds.model;

import java.io.Serializable;

/**
 * Created by Eric on 24/11/15.
 */
public class User implements Serializable{
    public long id;
    public String login;
    public String avatar;
    public String coverPicture;
    public String email;
}
