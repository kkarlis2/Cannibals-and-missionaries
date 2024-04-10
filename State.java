import java.util.ArrayList;
import java.util.List;





public class State {

    
    public String boat_side;
    public int CannibalsLeft,IerapostoloiLeft,transport_limit;
    private int CannibalsRight,IerapostoloiRight;
    private int Cannibals_on_boat,Ierapostoloi_on_boat;

    private State father;
    //Gia na apothikeyei thn twrinh katastash
    public State(int CannibalsLeft,int IerapostoloiLeft,String boat_side,int transport_limit,int CannibalsRight,int IerapostoloiRight){
        this.transport_limit=transport_limit;
        this.CannibalsLeft=CannibalsLeft;
        this.IerapostoloiLeft=IerapostoloiLeft;
        this.CannibalsRight=CannibalsRight;
        this.IerapostoloiRight=IerapostoloiRight;
        this.boat_side=boat_side;
    }

//-----------------aparaithtoi getters kai setters-------------------
    
    public int getCannibalsLeft(){
        return CannibalsLeft;
    }

    public int getCannibalsRight(){
        return CannibalsRight;
    }

    public int getIerapostolousLeft(){
        return IerapostoloiLeft;
    }

    public int getIerapostolousRight(){
        return IerapostoloiRight;
    }

    public void setCannibalsLeft(int CannibalsLeft){
        this.CannibalsLeft=CannibalsLeft;
    }

    public void setCannibalsRight(int CannibalsRight){
        this.CannibalsRight=CannibalsRight;
    }

    public void setIerapostolousLeft(int IerapostoloiLeft){
        this.IerapostoloiLeft=IerapostoloiLeft;
    }

    public void setIerapostolousRight(int IerapostoloiRight){
        this.IerapostoloiRight=IerapostoloiRight;
    }

    public int getIerapostolous_on_boat(){
        return Ierapostoloi_on_boat;
    }
    public int getCannibals_on_boat(){
        return Cannibals_on_boat;
    }

    public void setCannibals_on_boat(int Cannibals_on_boat){
        this.Cannibals_on_boat=Cannibals_on_boat;
    }

    public void setIerapostolous_on_boat(int Ierapostoloi_on_boat){
        this.Ierapostoloi_on_boat=Ierapostoloi_on_boat;
    }
    public void goLeft(){
        this.boat_side="LEFT";
    }

    public void goRight(){
        this.boat_side="RIGHT";
    }

    public boolean isLeft(){
        return boat_side=="LEFT";
    }

    public boolean isRight(){
        return boat_side=="RIGHT";
    }

    public State getFather(){
        return father;
    }


    public void setFather(State father){
        this.father=father;
    }

    public boolean isFinal(){//elegxoume an mia katastash einai telikh
        return CannibalsLeft==0 && IerapostoloiLeft==0;
    }

    public boolean isValid(){//me bash tous periorismous tou problhmatos elegxoyme an mia katastash einai egkyrh
        if (IerapostoloiLeft >= 0 && IerapostoloiRight >= 0 && CannibalsLeft >= 0 && CannibalsRight >= 0
	               && (IerapostoloiLeft == 0 || IerapostoloiLeft >= CannibalsLeft)
	               && (IerapostoloiRight == 0 || IerapostoloiRight >= CannibalsRight)&&(Ierapostoloi_on_boat==0||Ierapostoloi_on_boat>=Cannibals_on_boat)) {
			return true;
		}
		return false;
    }

//--------- testaroume mia katastash kai an mas kanei thn prosthetoume----------
    private void check_movements(List<State> success,State newState){
        if(this.boat_side=="LEFT"){
            setCannibals_on_boat(Math.abs(this.CannibalsLeft-newState.CannibalsLeft));
            setIerapostolous_on_boat(Math.abs(this.IerapostoloiLeft-newState.IerapostoloiLeft));
        }else{
            setCannibals_on_boat(Math.abs(this.CannibalsRight-newState.CannibalsRight));
            setIerapostolous_on_boat(Math.abs(this.IerapostoloiRight-newState.IerapostoloiRight));
        }
        if(newState.isValid()){
            newState.setFather(this);
            success.add(newState);
        }
    }
//-------- h eyretikh synarthsh tou frontistiriou------------
    public int getHeightOfTree(){ 
        State a=this.father;
        int k=0;
        if(a==null){
            return k;
        }
        while(a.father!=null){
            k++;
            a=a.father;
        }
        return k;
    }
    public int heuristic(){
       if(CannibalsLeft+IerapostoloiLeft==0){
           return 0;
       }
       else if (boat_side=="RIGHT"){
           if(CannibalsLeft+IerapostoloiLeft>0){
               return (2*(CannibalsLeft+IerapostoloiLeft))+getHeightOfTree();
           }
       }
       else if(boat_side=="LEFT"){
           if(CannibalsLeft+IerapostoloiLeft==1){
               return 1+getHeightOfTree();
           }else if(CannibalsLeft+IerapostoloiLeft>1){
               
               if(this.father==null){
                   return(2*(CannibalsLeft+IerapostoloiLeft)-transport_limit);
               }else{
                    return (2*(CannibalsLeft+IerapostoloiLeft)-transport_limit)+getHeightOfTree();
                }
            }
       }
       return -1;
   }
   
//---------elegxoume oles tis pithanes metakinhseis kai kratame tis egkyres------------ 
    public List<State> Success_Movements(){
        List<State> success=new ArrayList<State>();
        if (boat_side== "LEFT") {
            
            for(int i=0;i<=transport_limit;i++){
                check_movements(success, new State(CannibalsLeft - i, IerapostoloiLeft - (transport_limit-i), "RIGHT",transport_limit,CannibalsRight + i, IerapostoloiRight + (transport_limit-i))); // Two missionaries cross left to right.
            }
            for(int i=transport_limit-1;i>0;i--){
                check_movements(success, new State(CannibalsLeft - i, IerapostoloiLeft , "RIGHT",transport_limit,CannibalsRight + i, IerapostoloiRight)); // Two missionaries cross left to right.
                check_movements(success, new State(CannibalsLeft , IerapostoloiLeft-i, "RIGHT",transport_limit,CannibalsRight , IerapostoloiRight +i)); // Two missionaries cross left to right.
            }
            
		} else {
            for(int i=transport_limit-1;i>0;i--){
                check_movements(success, new State(CannibalsLeft+i, IerapostoloiLeft , "LEFT",transport_limit,CannibalsRight-i, IerapostoloiRight )); // Two missionaries cross right to left.
                check_movements(success, new State(CannibalsLeft, IerapostoloiLeft + i, "LEFT",transport_limit,CannibalsRight, IerapostoloiRight - i)); // Two missionaries cross right to left.
            }
            for(int i=0;i<=transport_limit;i++){
                check_movements(success, new State(CannibalsLeft+i, IerapostoloiLeft + (transport_limit-i), "LEFT",transport_limit,CannibalsRight-i, IerapostoloiRight - (transport_limit-i))); // Two missionaries cross right to left.
            }
            
        }
		return success;

    }



    public String toString() {
		if (boat_side== "LEFT") {
			return "(" + CannibalsLeft + "," + IerapostoloiLeft + ",L,"
        			+ CannibalsRight + "," + IerapostoloiRight + ")";
		} else {
			return "(" + CannibalsLeft + "," + IerapostoloiLeft + ",R,"
        			+ CannibalsRight + "," + IerapostoloiRight + ")";
		}
     }

     @Override
     public boolean equals(Object obj) {
         if (!(obj instanceof State)) {
             return false;
         }
         State s = (State) obj;
         return (s.CannibalsLeft == CannibalsLeft && s.IerapostoloiLeft == IerapostoloiLeft
                 && s.boat_side == boat_side && s.CannibalsRight == CannibalsRight
                 && s.IerapostoloiRight == IerapostoloiRight);
     }
}
