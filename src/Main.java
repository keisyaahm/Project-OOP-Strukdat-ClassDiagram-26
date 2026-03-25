abstract class Member {
    protected String nama;
    protected int umur;

    public Member (String nama, int umur){
        this.nama = nama;
        this.umur = umur;
    }

    public String getNama(){
        return nama;
    }
    public int getUmur(){
        return umur;
    }

    public abstract String getKategori();
}

//Mem inti
class MemberInti extends Member{
    public MemberInti(String nama, int umur){
        super(nama, umur); //construktor inheritance
    }

    @Override
    public String getKategori(){
        return "Member Inti";
    }
}
//Mem trainee
class MemberTrainee extends Member{
    public MemberTrainee (String nama, int umur){
        super(nama, umur);
    }
    @Override
    public String getKategori(){
        return "Trainee";
    }
}

//kontendig
abstract class KontenDigital{
    private String idLaporan;
    private Member targetMember;
    private double aiScore;
    private double lvlBahaya;
    private boolean isFlag;

    public KontenDigital(String idLaporan, Member targetMember, double aiScore, double lvlBahaya){
        this.idLaporan=idLaporan;
        this.targetMember=targetMember;
        this.aiScore=aiScore;
        this.lvlBahaya= lvlBahaya;
        this.isFlag= false;
    }

    //skor akhir
    public abstract double kalkuSkorAkhir();

    public String getIdLaporan(){
        return idLaporan;
    }
    public Member getTargetMember(){
        return targetMember;
    }
    public double getAiScore(){
        return aiScore;
    }
    public double getLvlBahaya(){
        return lvlBahaya;
    }
    public boolean isFlag(){
        return isFlag;
    }

    public void setFLag(boolean status){
        this.isFlag=status;
    }

    public void setFlag(boolean status) {
        this.isFlag = status;
    }
}

//-konten sosmed
class KontenSosmed extends KontenDigital{
    private String platform;
    private String tanggal;

    public KontenSosmed(String idLaporan, Member targetMember, double aiScore, double lvlBahaya, String platform, String tanggal){
        super(idLaporan, targetMember, aiScore, lvlBahaya);
        this.platform=platform;
        this.tanggal= tanggal;
    }

    public String getPlatform(){
        return platform;
    }
    public String getTanggal(){
        return tanggal;
    }

    @Override
    public double kalkuSkorAkhir(){
        return (getAiScore() + getLvlBahaya()) / 2.0;
    }
}

//scanner
class Scanner{
    private double batasBahaya;

    public Scanner(double batasBahaya){
        this.batasBahaya=batasBahaya;
    }

    public void analisis (KontenSosmed konten){
        double skor = konten.kalkuSkorAkhir();

        if (skor>=batasBahaya){
            konten.setFlag(true);
        }else{
            konten.setFlag(false);
            System.out.printf("[AMANNN] Target: %-20s | Platform: %-12s | Skor: %.1f%%\n", 
                konten.getTargetMember().getNama(),
                konten.getPlatform(),
                skor);
        }
    }
}


//aksi
class AksiManagement{
    private String namaDev;

    public AksiManagement(String namaDev){
        this.namaDev= namaDev;
    }

    public void prosesHukum(KontenSosmed konten){
        if (!konten.isFlag())
            return;

        double skor = konten.kalkuSkorAkhir();
        Member korban = konten.getTargetMember();

        //dibawah umur
        String urgensi = (korban.getUmur()<18)
            ? "[URGENTT PRIORITAS 1 DBU]"
            : "[PRIORITAS 2]";

        System.out.println("=== LAPORAN TERDETEKSI ===");
        System.out.println("Divisi:" + namaDev);
        System.out.println("ID Konten:" + konten.getIdLaporan());
        System.out.println("Platform:" + konten.getPlatform());
        System.out.printf("Target:+ %s (%s, %d tahun\n", korban.getNama(), korban.getKategori(), korban.getUmur());
        System.out.printf("AI Score: + %.2f%%\n", konten.getAiScore());
        System.out.printf("Lvl Bahaya: %.1f%%\n", konten.getLvlBahaya());
        System.out.printf("Skor Akhir: %.1f%% (batas: 70.0%%)\n", skor);
        System.out.println("Status: FLAGGED");
        System.out.println("Urgensi: " + urgensi);
        System.out.println("Rekomendasi: Takedown + Laporan ke Polisi (UU ITE)");
        System.out.println("Tanggal: " + konten.getTanggal());
    }
}

//main
public class Main{
    public static void main(String[] args){
        Scanner scanner = new Scanner(70.0);
        AksiManagement management = new AksiManagement("Tech Team JKT48");

        //member
        MemberTrainee ekin = new MemberTrainee("Jacqueline Immanuela", 16);
        MemberTrainee intan = new MemberTrainee("Nur Intan", 20);
        MemberInti freya = new MemberInti("Raden Rara Freyanashifa Jayawardana", 20);
        MemberInti indah = new MemberInti("Indah Cahya Nabila", 25);

        //konten sosmed
        KontenSosmed kasus1 = new KontenSosmed(
            "CNT-089296", ekin, 61.86, 95.0, "TikTok", "25 Maret 2026");
        KontenSosmed kasus2 = new KontenSosmed(
            "CNT-080911", intan, 12.0, 10.0, "TikTok", "25 Maret 2026");
        KontenSosmed kasus3 = new KontenSosmed(
            "CNT-880912", freya, 93.67, 65.0, "Twitter", "07 Januari 2026");
        KontenSosmed kasus4 = new KontenSosmed(
            "CNT-880913", indah, 15.0, 5.0, "Instagram", "25 Maret 2026");

        System.out.println("=== DETEKSI AI DEEPFAKE ===\n");
        
        scanner.analisis(kasus1); management.prosesHukum(kasus1);
        scanner.analisis(kasus2); management.prosesHukum(kasus2);
        scanner.analisis(kasus3); management.prosesHukum(kasus3);
        scanner.analisis(kasus4); management.prosesHukum(kasus4);

        System.out.println("\n=== SELESAI ===");
    }
}
