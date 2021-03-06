package io.github.avatarhurden.tribalwarsengine.ferramentas.assistente_saque;

import io.github.avatarhurden.tribalwarsengine.components.CoordenadaPanel;
import io.github.avatarhurden.tribalwarsengine.components.TimeFormattedJLabel;
import io.github.avatarhurden.tribalwarsengine.enums.Cores;
import io.github.avatarhurden.tribalwarsengine.objects.building.BuildingBlock;
import io.github.avatarhurden.tribalwarsengine.objects.building.BuildingBlock.BuildingsEditPanel;
import io.github.avatarhurden.tribalwarsengine.tools.property_classes.OnChange;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import config.Lang;

/**
 * Panel para inser��o e output dos dados relativo ao c�lculo do intervalo de tempo
 * entre ataques. Basicamente, dados relacionados � aldeia de origem
 * 
 * @author Arthur
 *
 */
public class PanelIntervalo extends JPanel{

	private CoordenadaPanel coordenadas;
	private BuildingBlock buildings;
	private BuildingsEditPanel buildingsEdit;
	
	private TimeFormattedJLabel respostaLabel;
	private JLabel errorMessage;
	
	protected PanelIntervalo(OnChange onChange) {
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0 };
		gridBagLayout.rowHeights = new int[] { 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
		setLayout(gridBagLayout);
		
		setOpaque(false);
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 0;
		c.gridx = 0;
		
		buildings = new BuildingBlock("wood", "stone", "iron", "storage", "hide");
		buildingsEdit = buildings.new BuildingsEditPanel(onChange, true, true, true);
		
		// Adds the village coordinates
		coordenadas = new CoordenadaPanel(Lang.AldeiaDestino.toString(), onChange);
		
		c.gridwidth = 2;
		c.insets = new Insets(0, 0, 10, 0);
		add(coordenadas, c);
	
		c.gridy++;
		add(buildingsEdit, c);
		
		// Adds the panel with the interval
		c.gridy++;
		c.gridx = 0;
		c.gridwidth = 2;
		c.insets = new Insets(25, 5, 5, 5);
		add(makePanelResposta(), c);
	
		// Adding error message
		
		errorMessage = new JLabel(" ", SwingConstants.CENTER);
		errorMessage.setForeground(Color.RED);
		errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
		
		c.gridy++;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(10, 0, 0, 0);
		add(errorMessage, c);
	}
	
	private JPanel makePanelResposta() {
		
		JPanel panel = new JPanel(new GridLayout(0,1));
		panel.setOpaque(false);
		
		// Add panel de �ltimo ataque
		JPanel ataquePanel = new JPanel();
		ataquePanel.add(new JLabel("Enviar ataques a cada"));
			
		ataquePanel.setBackground(Cores.FUNDO_ESCURO);
		ataquePanel.setBorder(new MatteBorder(1, 1, 1, 1,Cores.SEPARAR_ESCURO));
				
		panel.add(ataquePanel);
				
		// Add panel de hor�rio
		
		respostaLabel = new TimeFormattedJLabel(false);
		
		JPanel horaPanel = new JPanel();
		horaPanel.add(respostaLabel);
						
		horaPanel.setBackground(Cores.ALTERNAR_ESCURO);
		horaPanel.setBorder(new MatteBorder(0, 1, 1, 1,Cores.SEPARAR_ESCURO));
				
		panel.add(horaPanel);
		
		return panel;
	}
	
	protected BuildingBlock getBuildings() {
		return buildings;
	}	
	
	protected BuildingsEditPanel getBuildingsEdit() {
		return buildingsEdit;
	}
	
	/**
	 * Retorna o panel com as coordenadas da aldeia de destino
	 * @return CoordenadaPanel
	 */
	protected CoordenadaPanel getCoordenadaDestino() {
		return coordenadas;
	}
	
	protected TimeFormattedJLabel getIntervalLabel() {
		return respostaLabel;
	}
	
	/**
	 * Sets the panel with a format time (value given in milliseconds)
	 * @param millis
	 */
	protected void setDisplayIntervalo(long millis){
		errorMessage.setText(" ");
		
		respostaLabel.setTime(millis);
		
	}
	
	protected void setErrorMessage(String error) {
		respostaLabel.setText("");
		
		errorMessage.setText(error);
	}
	
	protected void resetAll() {
		
		coordenadas.reset();
		
		buildingsEdit.resetComponents();
		
		respostaLabel.setText("");
		
	}
}
