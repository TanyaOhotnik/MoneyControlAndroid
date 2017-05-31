package com.tanyaohotnik.moneycontrol.dao;

import android.content.Context;
import android.os.Environment;

import com.tanyaohotnik.moneycontrol.dao.interfaces.IUser;
import com.tanyaohotnik.moneycontrol.entities.CashTransaction;
import com.tanyaohotnik.moneycontrol.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanya Ohotnik on 26.03.2017.
 */

public class UserDAO extends AbstractDAO<User> implements IUser<User> {

    private static List<User> sUserList;

    public UserDAO(Context context) {
        super(context);
        if (sUserList == null)
            sUserList = getUserList();
    }

    private List<User> getUserList() {
        List<User> list = new ArrayList<>();
//do nothing

        return list;
    }
    private User getById(long id){
        for(User c:sUserList){
            if(c.getUserId()==id) return c;
        }
        return null;
    }


    @Override
    public User get(long id) {
        return getById(id);
    }

    @Override
    public Class getTypeClass() {
        return User.class;
    }

    @Override
    public User getUserByEmail(String email) {
        String query = "Select * from User where email = ? ";
        List<User> l = new ArrayList<>(1);
        l.addAll(User.findWithQuery(User.class, query, email));
        return l.get(0);
    }
    public File getPhotoFile(User user) {
        File externalFilesDir = mContext.
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir == null)
            return null;

            return
        new File(externalFilesDir, user.getPhotoFilename()+"jpeg");

    }
}
