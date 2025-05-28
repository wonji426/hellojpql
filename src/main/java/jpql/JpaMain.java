package jpql;

import jakarta.persistence.*;
import java.util.List;


public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("관리자1");
            member.setAge(10);
            member.setType(MemberType.ADMIN);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setAge(10);
            member2.setType(MemberType.ADMIN);

            member.setTeam(team);
            member2.setTeam(team);

            em.persist(member);
            em.persist(member2);

            em.flush();
            em.clear();

            String query1 = "select concat('a', 'b') from Member m";
            String query2 = "select substring(m.username, 2, 3) from Member m";
            String query3 = "select locate('de', 'abcdefg') from Member m";
            String query4 = "select size(t.members) from Team t";

            List<Integer> result = em.createQuery(query4, Integer.class).getResultList();

            for (Integer s : result) {
                System.out.println("s = " + s);
            }

            String query = "select group_concat(m.username) from Member m";
            List<String> result1 = em.createQuery(query, String.class).getResultList();
            for (String s : result1) {
                System.out.println("s = " + s);
            }

            tx.commit(); //커밋시 SQL문 나감
        } catch (Exception e) {
            tx.rollback();//에러 시 롤백
        } finally {
            em.close();//닫기
        }

        emf.close();
    }
}
