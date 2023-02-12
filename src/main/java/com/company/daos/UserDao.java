package com.company.daos;

import com.company.domains.UserDomain;
import lombok.NonNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDao extends Dao {

    private static final String INSERT_QUERY = "insert into users(chatid,username, password, firstname,language) values(?,?,?,?,?);";
    private static final String GET_ALL_QUERY = "select chatid, username, firstname, createdat, language from users";

    public void save(@NonNull UserDomain domain) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement pst = connection.prepareStatement(INSERT_QUERY);
        pst.setLong(1, domain.getChatID());
        pst.setString(2, domain.getUsername());
        pst.setString(3, domain.getPassword());
        pst.setString(4, domain.getFirstName());
        pst.setString(5, domain.getLanguage());
        pst.execute();
    }

    public List<UserDomain> findAll() throws SQLException {
        Connection connection = getConnection();
        PreparedStatement pst = connection.prepareStatement(GET_ALL_QUERY);
        ResultSet rs = pst.executeQuery();
        ArrayList<UserDomain> userDomains = new ArrayList<>();
        while (rs.next()) {
            userDomains.add(UserDomain.builder()
                    .chatID(rs.getLong("chatid"))
                    .username(rs.getString("username"))
                    .firstName(rs.getString("firstname"))
                    .createdAt(rs.getTimestamp("createdat").toLocalDateTime())
                    .language(rs.getString("language"))
                    .build());
        }
        return userDomains;
    }
}
