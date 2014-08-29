package io.github.avatarhurden.tribalwarsengine.ferramentas.recrutamento;

import io.github.avatarhurden.tribalwarsengine.components.TimeFormattedJLabel;
import io.github.avatarhurden.tribalwarsengine.objects.Army;
import io.github.avatarhurden.tribalwarsengine.objects.Army.ArmyEditPanel;
import io.github.avatarhurden.tribalwarsengine.objects.Army.Tropa;
import io.github.avatarhurden.tribalwarsengine.objects.Buildings;
import io.github.avatarhurden.tribalwarsengine.objects.Buildings.BuildingsEditPanel;
import io.github.avatarhurden.tribalwarsengine.tools.property_classes.OnChange;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import database.Cores;
import database.Unidade;

public class RecruitmentPanel extends JPanel {

	private Army army;
	private ArmyEditPanel armyEdit;
	private Buildings buildings;
	private BuildingsEditPanel buildingsEdit;
	
	private Map<Unidade, TimeFormattedJLabel> temposUnitarios;
	private Map<Unidade, TimeFormattedJLabel> temposTotais;
	
	private TimeFormattedJLabel tempoTotal;
	
	private OnChange onChange;
	
	protected RecruitmentPanel(OnChange onChange, Buildings builds, Army army) {
		this.onChange = onChange;
		this.army = army;
		this.buildings = builds;
		
		makePanels();
		
		makeGUI();
	}
	
	private void makePanels() {
		armyEdit = army.getEditPanelNoLevelsNoHeader(onChange);
		buildingsEdit = buildings.getEditPanelFullNoHeader(onChange);
		
		temposUnitarios = new HashMap<Unidade, TimeFormattedJLabel>();
		temposTotais = new HashMap<Unidade, TimeFormattedJLabel>();
		
		
		tempoTotal = new TimeFormattedJLabel(false);
	}
	
	private void makeGUI() {
		
		setBorder(new LineBorder(Cores.SEPARAR_ESCURO));
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		
		c.gridheight = army.getTropas().size();
		c.gridwidth = 2;
		add(armyEdit, c);
		
		c.gridheight = 1;
		c.gridx += 2;
		for (int i = 0; i < army.getTropas().size(); i++) {
			add(makeUnitTimePanel(army.getTropas().get(i).getUnidade(), Cores.getAlternar(i+1)), c);
			c.gridy++;
		}
		
		if (buildings.getEdif�cios().get(0).getN�velM�ximo() > 1) {
			c.gridwidth = 4;
			c.gridx = 0;
			add(makeBuildingPanel(), c);
		}
		
		System.out.println(getPreferredSize());
	}
	
	private JPanel makeBuildingPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(Cores.FUNDO_ESCURO);
		panel.setBorder(new MatteBorder(1, 0, 0, 0, Cores.SEPARAR_ESCURO));
		
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 125, 100, 100 , 100};
		panel.setLayout(layout);
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 0;

		buildingsEdit.setOpaquePanels(false);
		c.gridwidth = 2;
		panel.add(buildingsEdit, c);
		
		c.gridwidth = 1;
		c.gridx += 2;
		panel.add(new JLabel("Tempo Total:"), c);
		
		c.gridx++;
		panel.add(tempoTotal, c);
		
		return panel;
	}
	
	private JPanel makeUnitTimePanel(Unidade u, Color cor) {
		JPanel panel = new JPanel();
		panel.setBackground(cor);
		panel.setPreferredSize(new Dimension(200, 30));
		
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 100, 100 };
		panel.setLayout(layout);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		
		TimeFormattedJLabel unitaryTime = new TimeFormattedJLabel(false);
		temposUnitarios.put(u, unitaryTime);
		
		c.insets = new Insets(5, 0, 5, 5);
		panel.add(unitaryTime, c);

		TimeFormattedJLabel totalTime = new TimeFormattedJLabel(false);
		temposTotais.put(u, totalTime);
		
		c.insets = new Insets(0, 0, 5, 0);
		c.gridx++;
		panel.add(totalTime, c);
		
		return panel;
	}
	
	protected void changeValues() {
		
		buildingsEdit.saveValues();
		armyEdit.saveValues();
		
		double fator = buildings.getFatorProdu��oUnidade(buildings.getEdif�cios().get(0));
		
		for (Tropa t : army.getTropas()) {
			temposUnitarios.get(t.getUnidade()).setTime((long) (
					t.getUnidade().getTempoProdu��o()*fator));
			temposTotais.get(t.getUnidade()).setTime((long) (t.getTempoProdu��o()*fator));
		}
		
		tempoTotal.setTime((long) (army.getTempoProdu��o()*fator));
		
	}
	
	protected ArmyEditPanel getArmyEditPanel() {
		return armyEdit;
	}
	
}