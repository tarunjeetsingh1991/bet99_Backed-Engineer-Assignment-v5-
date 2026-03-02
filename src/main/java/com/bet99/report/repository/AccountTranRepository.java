package com.bet99.report.repository;

import com.bet99.report.entity.AccountTran;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class AccountTranRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public List<AccountTran> findReport(
            Timestamp start, Timestamp end,
            Integer accountId,
            String tranType,
            String gameId,
            String platformTranId,
            String gameTranId,
            String sortBy,
            String direction,
            int offset,
            int limit) {

        Session session = sessionFactory.getCurrentSession();

        StringBuilder hql = new StringBuilder("FROM AccountTran a WHERE a.datetime BETWEEN :start AND :end ");

        if (accountId != null) hql.append("AND a.accountId = :accountId ");
        if (tranType != null && !tranType.isEmpty()) hql.append("AND a.tranType = :tranType ");
        if (gameId != null && !gameId.isEmpty()) hql.append("AND a.gameId = :gameId ");
        if (platformTranId != null && !platformTranId.isEmpty()) hql.append("AND a.platformTranId = :platformTranId ");
        if (gameTranId != null && !gameTranId.isEmpty()) hql.append("AND a.gameTranId = :gameTranId ");

        String sortColumn = "a.datetime";
        if ("accountId".equals(sortBy)) sortColumn = "a.accountId";
        if ("tranType".equals(sortBy)) sortColumn = "a.tranType";
        if ("gameId".equals(sortBy)) sortColumn = "a.gameId";

        String sortDir = "asc".equalsIgnoreCase(direction) ? "asc" : "desc";

        hql.append("ORDER BY ").append(sortColumn).append(" ").append(sortDir);

        Query<AccountTran> query = session.createQuery(hql.toString(), AccountTran.class);
        query.setParameter("start", start);
        query.setParameter("end", end);

        if (accountId != null) query.setParameter("accountId", accountId);
        if (tranType != null && !tranType.isEmpty()) query.setParameter("tranType", tranType);
        if (gameId != null && !gameId.isEmpty()) query.setParameter("gameId", gameId);
        if (platformTranId != null && !platformTranId.isEmpty()) query.setParameter("platformTranId", platformTranId);
        if (gameTranId != null && !gameTranId.isEmpty()) query.setParameter("gameTranId", gameTranId);

        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.list();
    }

    public Long countReport(
            Timestamp start, Timestamp end,
            Integer accountId,
            String tranType,
            String gameId,
            String platformTranId,
            String gameTranId) {

        Session session = sessionFactory.getCurrentSession();

        StringBuilder hql = new StringBuilder("SELECT COUNT(a) FROM AccountTran a WHERE a.datetime BETWEEN :start AND :end ");

        if (accountId != null) hql.append("AND a.accountId = :accountId ");
        if (tranType != null && !tranType.isEmpty()) hql.append("AND a.tranType = :tranType ");
        if (gameId != null && !gameId.isEmpty()) hql.append("AND a.gameId = :gameId ");
        if (platformTranId != null && !platformTranId.isEmpty()) hql.append("AND a.platformTranId = :platformTranId ");
        if (gameTranId != null && !gameTranId.isEmpty()) hql.append("AND a.gameTranId = :gameTranId ");

        Query<Long> query = session.createQuery(hql.toString(), Long.class);
        query.setParameter("start", start);
        query.setParameter("end", end);

        if (accountId != null) query.setParameter("accountId", accountId);
        if (tranType != null && !tranType.isEmpty()) query.setParameter("tranType", tranType);
        if (gameId != null && !gameId.isEmpty()) query.setParameter("gameId", gameId);
        if (platformTranId != null && !platformTranId.isEmpty()) query.setParameter("platformTranId", platformTranId);
        if (gameTranId != null && !gameTranId.isEmpty()) query.setParameter("gameTranId", gameTranId);

        return query.uniqueResult();
    }
    
    public Object[] getSummary(
            Timestamp start, Timestamp end,
            Integer accountId,
            String tranType,
            String gameId,
            String platformTranId,
            String gameTranId) {

        Session session = sessionFactory.getCurrentSession();

        StringBuilder hql = new StringBuilder(
                "SELECT " +
                "SUM(CASE WHEN a.tranType='GAME_BET' THEN a.amountReal ELSE 0 END), " +
                "SUM(CASE WHEN a.tranType='GAME_WIN' THEN a.amountReal ELSE 0 END) " +
                "FROM AccountTran a WHERE a.datetime BETWEEN :start AND :end ");

        if (accountId != null) hql.append("AND a.accountId = :accountId ");
        if (tranType != null && !tranType.isEmpty()) hql.append("AND a.tranType = :tranType ");
        if (gameId != null && !gameId.isEmpty()) hql.append("AND a.gameId = :gameId ");
        if (platformTranId != null && !platformTranId.isEmpty()) hql.append("AND a.platformTranId = :platformTranId ");
        if (gameTranId != null && !gameTranId.isEmpty()) hql.append("AND a.gameTranId = :gameTranId ");

        Query<Object[]> query = session.createQuery(hql.toString(), Object[].class);

        query.setParameter("start", start);
        query.setParameter("end", end);

        if (accountId != null) query.setParameter("accountId", accountId);
        if (tranType != null && !tranType.isEmpty()) query.setParameter("tranType", tranType);
        if (gameId != null && !gameId.isEmpty()) query.setParameter("gameId", gameId);
        if (platformTranId != null && !platformTranId.isEmpty()) query.setParameter("platformTranId", platformTranId);
        if (gameTranId != null && !gameTranId.isEmpty()) query.setParameter("gameTranId", gameTranId);

        return query.uniqueResult();
    }
}