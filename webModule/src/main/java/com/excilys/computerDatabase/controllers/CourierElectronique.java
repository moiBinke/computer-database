package com.excilys.computerDatabase.controllers;
import java.io.Serializable;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 

public class CourierElectronique {

	private String adresseExpediteur;
	private String motDePasseExpediteur;
	private String sujet;
	private String corpsDuCourier;
	private String adresseDestinataire;
	
	
	
	public CourierElectronique(String adresseExpediteur, String motDePasseExpediteur, String sujet,
			String corpsDuCourier, String adresseDestinataire) {
		super();
		this.adresseExpediteur = adresseExpediteur;
		this.motDePasseExpediteur = motDePasseExpediteur;
		this.sujet = sujet;
		this.corpsDuCourier = corpsDuCourier;
		this.adresseDestinataire = adresseDestinataire;
	}



	public CourierElectronique() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getAdresseExpediteur() {
		return adresseExpediteur;
	}



	public void setAdresseExpediteur(String adresseExpediteur) {
		this.adresseExpediteur = adresseExpediteur;
	}



	public String getMotDePasseExpediteur() {
		return motDePasseExpediteur;
	}



	public void setMotDePasseExpediteur(String motDePasseExpediteur) {
		this.motDePasseExpediteur = motDePasseExpediteur;
	}



	public String getSujet() {
		return sujet;
	}



	public void setSujet(String sujet) {
		this.sujet = sujet;
	}



	public String getCorpsDuCourier() {
		return corpsDuCourier;
	}



	public void setCorpsDuCourier(String corpsDuCourier) {
		this.corpsDuCourier = corpsDuCourier;
	}



	public String getAdresseDestinataire() {
		return adresseDestinataire;
	}



	public void setAdresseDestinataire(String adresseDestinataire) {
		this.adresseDestinataire = adresseDestinataire;
	}



	public void envoyer() {
		// Création de la session
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.host","smtp.gmail.com");
		props.put("mail.smtp.port","587");
		Session session = Session.getInstance(props,
		new javax.mail.Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(adresseExpediteur, motDePasseExpediteur);
		}
		});
		try {
			// Création de l'objet Message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.adresseExpediteur));
			message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(this.adresseDestinataire));
			message.setSubject(this.sujet);
			message.setText(this.corpsDuCourier);
			// Envoyer le message
			Transport.send(message);
			System.out.println("Message_envoyé");
			} catch (MessagingException e) {
			throw new RuntimeException(e);
			} }
}
