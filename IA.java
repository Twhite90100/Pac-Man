```java
public class IA{
    private int vitesse;
    // Vitesse de déplacement détermniée en fontion de la difficulté
    // Difficulté (Elle va augmenter en fonction des niveaux)
    private int x;
    private int y;
    private int SEUIL_DETECTION_JOUEUR;
    private boolean estVulnerable;
    final int XpositionParDefaut = 0;
    final int YpositionParDefaut = 0;

    public IA(){

    }
    public IA(int uneVitesse,int uneDifficulte){
        this.vitesse = uneVitesse;
        this.difficulte = uneDifficulte;
    }
    public void spawn(){
        this.estVulnerable = false;
        this.x = XpositionParDefaut;
        this.y = YpositionParDefaut;
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    public void rendreVulnerable(){
        this.estVulnerable = true;
    }
    public void retirerVulnerabiité(){
        this.estVulnerable = true;
    }
    public void estTouche(){
        this.x = XpositionParDefaut;
        this.y = YpositionParDefaut;
    }
    public void estSurMemeCaseQueJoueur(Joueur j){
        j.vies --;
        j.respawn;
        spawn();
    }
    public boolean joueurAuxAlentours(){
        if(distanceParRapportJoueur()<SEUIL_DETECTION_JOUEUR){
            return false;
        }
        else{
            return true;
        }
    }
    public void promener(){
        if(!joueurAuxAlentours()){
            if(!estAUneIntersection()){
                avance();
            }else{
                double choix = Math.random()*4;
                switch(choix){
                    case 0:
                        avance();
                        break;
                    case 1:
                        droite();
                        break;
                    case 2:
                        gauche();
                        break;
                    case 3:
                        demiTour();
                        break;
                }

            }
        }
    }
    private void avance(){

    }
    private void demiTour(){

    }
    private void droite(){

    }
    private void gauche(){

    }
}
```