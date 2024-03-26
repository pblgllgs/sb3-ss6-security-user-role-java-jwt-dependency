package com.pblgllgs.sb3ss6securityuserrolejjwt.repository;

import com.pblgllgs.sb3ss6securityuserrolejjwt.model.Role;
import com.pblgllgs.sb3ss6securityuserrolejjwt.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 *
 * @author pblgl
 * Created on 26-03-2024
 *
 */
@Repository
public class RoleCustomRepo {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Role> getRole(User user) {
        StringBuilder sql = new StringBuilder()
                .append("""
                        SELECT r.name as name
                         FROM users u
                         join users_roles ur on u.user_id=ur.user_id
                         join roles r on r.id = ur.role_id
                         where 1=1""");
        if (user.getEmail() != null) {
            sql.append(" and email=:email");
        }
        NativeQuery<Role> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());
        if (user.getEmail() != null) {
            query.setParameter("email", user.getEmail());
        }
        query.addScalar("name", StandardBasicTypes.STRING);
        query.setResultTransformer(Transformers.aliasToBean(Role.class));
        return query.list();
    }
}
