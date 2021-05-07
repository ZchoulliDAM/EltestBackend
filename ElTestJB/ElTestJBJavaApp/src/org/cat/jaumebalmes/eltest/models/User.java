
package org.cat.jaumebalmes.eltest.models;


/**
 *
 * @author Zaid
 */
public class User {
    //Attrubutes
    private int id;
    private String userName;
    private String mail;
    private String password;

    
    //Constructors
   
    
    public User() {
    }

    public User(int id, String userName, String mail, String password) {
        this.id = id;
        this.userName = userName;
        this.mail = mail;
        this.password = password;
    }

    
    
    
    
    

    public User(int userId) {
        this.id = userId;
    }

    public User(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    //Accesors
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 
     * @return user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 
     * @param userName : User name to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 
     * @return mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * 
     * @param mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userName=" + userName + ", mail=" + mail + ", password=" + password + '}';
    }



    
}
