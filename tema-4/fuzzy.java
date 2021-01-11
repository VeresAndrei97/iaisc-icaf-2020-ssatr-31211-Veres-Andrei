package net.agten.heatersimulator.domain.algorithm;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;
public class fuzzy implements ControllerAlgorithm {
    FIS fis;
    FunctionBlock fb;
    public short targetAdc;
    String filename = "fuzzyControl.fcl";
    public fuzzy(){

        this.fis = FIS.load(filename,true);
        if (this.fis == null) {
            System.err.println("Can't load file: '" + filename + "'");
            System.exit(1);
        }
        this.fb = fis.getFunctionBlock(null);

    }
    public short nextValue(short curAdc) {
        //int error = this.targetAdc - curAdc;
        this.fb.setVariable("targetAdc",this.targetAdc);
        this.fb.setVariable("curAdc",curAdc);
        this.fb.evaluate();

        //result = Math.max(Math.min(result,MAX_RESULT), 0);

        return (short)fb.getVariable("result").defuzzify();
    }
    public void setTargetAdc(short targetAdc) {
        this.targetAdc = targetAdc;
    }
}