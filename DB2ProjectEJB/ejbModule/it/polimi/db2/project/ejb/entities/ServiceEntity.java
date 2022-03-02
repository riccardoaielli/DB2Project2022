//package it.polimi.db2.project.ejb.entities;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "product")
//@NamedQueries({
//        @NamedQuery(name = "ProductEntity.findAll", query = "SELECT p FROM ProductEntity p"),
//        @NamedQuery(name = "ProductEntity.findByDate", query = "SELECT p FROM ProductEntity p INNER JOIN p.questionnaires q WHERE q.date = :date"),
//})
//public class ServiceEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "Id", nullable = false)
//    private int id;
//
//    @Column(name = "Name", nullable = false, length = 45)
//    private String name;
//
//    @Column(name = "Image", nullable = false, length = 45)
//    private String image;
//
//    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
//    private List<ReviewEntity> reviews = new ArrayList<>();
//
//    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
//    private List<QuestionnaireEntity> questionnaires = new ArrayList<>();
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public List<QuestionnaireEntity> getQuestionnaires() {
//        return questionnaires;
//    }
//
//    public void addQuestionnaire(QuestionnaireEntity questionnaire) {
//        getQuestionnaires().add(questionnaire);
//        questionnaire.setProduct(this);
//    }
//
//    public void removeQuestionnaire(QuestionnaireEntity questionnaire) {
//        getQuestionnaires().remove(questionnaire);
//    }
//
//    public List<ReviewEntity> getReviews() {
//        return reviews;
//    }
//}
