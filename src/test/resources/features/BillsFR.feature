#language: fr

Fonctionnalité: Générer des factures
  Contexte: un employé s'occupe des livraisons un autre s'occupe des factures

  Scénario: Il ya plusieurs livraisons à faire pour plusieurs fournisseur
    Quand l'employé envoie les 2 livraisons du fournisseurs AM et PK
    Alors 2 factures sont générées