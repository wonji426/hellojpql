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

            String query = "select m.team from Member m";
            List<Team> result = em.createQuery(query, Team.class).getResultList();

            for (Team s : result) {
                System.out.println("s = " + s);
            }

            String query2 = "select t.members from Team t";
            List<Member> result2 = em.createQuery(query2, Member.class).getResultList();

            for (Member s : result2) {
                System.out.println("s = " + s);
            }

            String query3 = "select SIZE(t.members) from Team t";
            List<String> result3 = em.createQuery(query3, String.class).getResultList();
            System.out.println("result3 = " + result3);

            String query4 = "select m.username from Team t join t.members m";
            List<String> result4 = em.createQuery(query4, String.class).getResultList();
            System.out.println("result3 = " + result4);


            tx.commit(); //커밋시 SQL문 나감
        } catch (Exception e) {
            tx.rollback();//에러 시 롤백
        } finally {
            em.close();//닫기
        }

        emf.close();
    }
}
