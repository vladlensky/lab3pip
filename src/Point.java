public class Point {
    private double x;
    private double y;
    private  double r;
    private boolean inArea;
    Point(double X,double Y,double R){
        x = X;
        y = Y;
        r = R;
        setInArea(checkArea(x,y,r));
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getR() {
        return r;
    }

    public boolean isInArea() {
        return inArea;
    }

    public void setInArea(boolean inArea) {
        this.inArea = inArea;
    }

    public void setR(double r) {
        this.r = r;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }

    public boolean checkArea(double x,double y,double R){
        if(x<=0&&x>=-R&&y>=0&&y<=(R/2)){
            return true;
        }
        if(x<=0&&y<=0&&-y-x<=R){
            return true;
        }
        if(x>=0&&y<=0&&y*y+x*x<=R*R){
            return true;
        }
        return false;
    }
}