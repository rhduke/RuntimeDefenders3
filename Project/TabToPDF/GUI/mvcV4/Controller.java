package mvcV4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tabparts.AutofixLog;
import tabparts.LargeNumberException;
import version13.CannotReadFileException;
import version13.ConversionException;
import version13.EmptyFileException;
import version13.NoFileExistsException;
import version13.NoMusicException;
import version13.TextToPDF;

import com.itextpdf.text.*;

public class Controller
{

	private static Model model;
	private View view;
	private static boolean active = true;

	public Controller(View view)
	{
		this.view = view;

		// this.view.addConvertButtonListener(new ConvertButtonListener());
		this.view.addSelectButtonListener(new SelectButtonListener());
		this.view.addSaveButtonListener(new SaveButtonListener());
		this.view.addCorrectionButtonListener(new CorrectionButtonListener());
		this.view.spacingListener(new SpacingListener());
		this.view.titleListener(new TitleListener());
		this.view.subtitleListener(new SubtitleListener());
		this.view.titleFocusListener(new TitleFocusListener());
		this.view.subtitleFocusListener(new SubtitleFocusListener());
		this.view.elementSizeListener(new ElementSizeListener());
		this.view.measureSpaceListener(new MeasureSpaceListener());
		this.view.titleFontSizeListener(new TitleFontSizeListener());
		this.view.subtitleFontSizeListener(new SubtitleFontSizeListener());
		this.view.leftMarginListener(new LeftMarginListener());
		this.view.rightMarginListener(new RightMarginListener());
		this.view.pageSizeListener(new PageSizeListener());
	}

	public static void displayError(String error)
	{
		View.showError(error);
	}

	/*
	 * protected static void setWriter(TextToPDF test2) { test = test2; }
	 * 
	 * protected static TextToPDF getWriter() { return test; }
	 */

	protected static void setModel(Model model2)
	{
		model = model2;
	}

	public static Model getModel()
	{
		return model;
	}
}

class TitleFocusListener implements FocusListener
{
	Model model = Controller.getModel();

	@Override
	public void focusGained(FocusEvent e)
	{
	}

	@Override
	public void focusLost(FocusEvent e)
	{
		Model model = Controller.getModel();
		try
		{
			/* If the value didn't change from the current value then do nothing */
			if (!model.getTitle().equals(View.title.getText()))
			{
				model.setTitle(View.title.getText());
				model.converter.updateTitle((View.title.getText()));
				IMGCreator.createPreview(model);

				// CHECK IF CONVERSION WAS DONE PROPERLY.
				String image = IMGCreator.getLastConverted();
				View.repaintPreview(image);
			}

		} catch (ConversionException e1)
		{
			Controller.displayError(e1.getMessage());
		}
	}
}

class TitleListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Model model = Controller.getModel();
		try
		{
			/* If the value didn't change from the current value then do nothing */
			if (!model.getTitle().equals(View.title.getText()))
			{
				model.setTitle(View.title.getText());
				model.converter.updateTitle((View.title.getText()));
				IMGCreator.createPreview(model);

				// CHECK IF CONVERSION WAS DONE PROPERLY.

				String image = IMGCreator.getLastConverted();
				View.repaintPreview(image);
			}

		} catch (ConversionException e1)
		{
			Controller.displayError(e1.getMessage());
		}
	}
}

class SubtitleFocusListener implements FocusListener
{
	Model model = Controller.getModel();

	@Override
	public void focusGained(FocusEvent e)
	{
	}

	@Override
	public void focusLost(FocusEvent e)
	{
		Model model = Controller.getModel();
		try
		{
			/* If the value didn't change from the current value then do nothing */
			if (!model.getSubTitle().equals(View.subtitle.getText()))
			{
				model.setSubTitle(View.subtitle.getText());
				model.converter.updateSubtitle((View.subtitle.getText()));
				IMGCreator.createPreview(model);

				// CHECK IF CONVERSION WAS DONE PROPERLY.

				String image = IMGCreator.getLastConverted();
				View.repaintPreview(image);
			}

		} catch (ConversionException e1)
		{
			Controller.displayError(e1.getMessage());
		}
	}
}

class SubtitleListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Model model = Controller.getModel();
		try
		{
			/* If the value didn't change from the current value then do nothing */
			if (!model.getSubTitle().equals(View.subtitle.getText()))
			{
				model.setSubTitle(View.subtitle.getText());
				model.converter.updateSubtitle((View.subtitle.getText()));
				IMGCreator.createPreview(model);

				// CHECK IF CONVERSION WAS DONE PROPERLY.

				String image = IMGCreator.getLastConverted();
				View.repaintPreview(image);
			}

		} catch (ConversionException e1)
		{
			Controller.displayError(e1.getMessage());
		}
	}
}

class SelectButtonListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Model model = new Model();
		Controller.setModel(model);

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select Text File");
		FileTypeFilter text_filter = new FileTypeFilter("Text File *.txt",new String[] {".txt"});
		chooser.addChoosableFileFilter(text_filter);
		chooser.setFileFilter(text_filter);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			String filenameWithExtension = chooser.getSelectedFile().toString();

			model.setFilenameWithExtention(filenameWithExtension);
			model.setFilename(Utils.removeFileExtension(filenameWithExtension));

			View.input.setText(filenameWithExtension);
			View.setComponentsEnabled(false);
			View.resetView();
			View.repaintPreview("");
			// View.convertButton.setEnabled(true);

			try
			{
				model.initializeConverter();
				model.runConverter();

				IMGCreator.createPreview(model);

				// CHECK IF CONVERSION WAS DONE PROPERLY.
				String image2 = IMGCreator.getLastConverted();
				Rectangle pagesize = model.getPageSize();
				View.repaintPreview(image2, pagesize);
				// IF IT WAS, ENABLE ALL BUTTONS and populate the fields.

				// GET CONVERTED FIELD VALUES.
				// ENABLE FIELDS
				View.setComponentsEnabled(true);

				// SET FIELD VALUES
				View.title.setText(model.getTitle());
				View.subtitle.setText(model.getSubTitle());
				View.staffSpacing.setValue((int) model.getSpacing());
				View.elementSize.setValue(model.getElementSize());
				View.measureSpace.setValue((int) model.getMeasureSpace());
				View.titleFontSize.setValue((int) model.getTitleFontSize());
				View.subtitleFontSize.setValue((int) model
						.getSubTitleFontSize());
				View.leftMarginSpace.setValue((int) model.getLeftMargin());
				View.rightMarginSpace.setValue((int) model.getLeftMargin());
				View.pageList.setSelectedIndex(0);
				View.propertiesPane.getVerticalScrollBar().setValue(0);

				View.updateCorrection(model.getFilename());

				// ELSE display the error message and don't enable buttons.

			} catch (NoFileExistsException | CannotReadFileException
					| EmptyFileException | NoMusicException
					| LargeNumberException | ConversionException e1)
			{
				Controller.displayError(e1.getMessage());
			}
		}
	}
}

/**
 * Saves the file in the desired location with the desired name.
 * 
 * @author Skyler
 * 
 */
class SaveButtonListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Model model = Controller.getModel();
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select PDF Destination");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setAcceptAllFileFilterUsed(false);

		String input = model.getFilenameWithExtension();
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			String outputFilename = chooser.getSelectedFile().toString();
			TextToPDF test;
			try
			{
				test = new TextToPDF(outputFilename, input);
				test.WriteToPDF();

			} catch (NoFileExistsException | CannotReadFileException
					| EmptyFileException | NoMusicException
					| LargeNumberException | ConversionException e1)
			{
				Controller.displayError(e1.getMessage());
			}
		}
	}
}

class CorrectionButtonListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		View.correctionLogDialog.setLocationRelativeTo(View.previewPane
				.getVerticalScrollBar());
		View.correctionLogScroller.getVerticalScrollBar().setValue(0);
		View.correctionLogScroller.getHorizontalScrollBar().setValue(0);
		View.correctionLogDialog.setVisible(true);
	}
}

/*
 * class ConvertButtonListener implements ActionListener {
 * 
 * @Override public void actionPerformed(ActionEvent e) { Model model =
 * Controller.getModel();
 * 
 * try { model.initializeConverter(); model.runConverter();
 * 
 * IMGCreator.createPreview(model);
 * 
 * // CHECK IF CONVERSION WAS DONE PROPERLY. String image2 =
 * IMGCreator.getLastConverted(); Rectangle pagesize = model
 * .convertPageSizeToRectangle((String) View.pageList .getSelectedItem());
 * View.repaintPreview(image2, pagesize); // IF IT WAS, ENABLE ALL BUTTONS and
 * populate the fields.
 * 
 * // GET CONVERTED FIELD VALUES. // ENABLE FIELDS View.title.setEnabled(true);
 * View.subtitle.setEnabled(true); View.staffSpacing.setEnabled(true);
 * View.elementSize.setEnabled(true); View.measureSpace.setEnabled(true);
 * View.titleFontSize.setEnabled(true); View.subtitleFontSize.setEnabled(true);
 * View.leftMarginSpace.setEnabled(true);
 * View.rightMarginSpace.setEnabled(true); View.pageList.setEnabled(true);
 * View.saveButton.setEnabled(true);
 * 
 * // SET FIELD VALUES View.title.setText(model.getTitle());
 * View.subtitle.setText(model.getSubTitle()); View.staffSpacing.setValue((int)
 * model.getSpacing()); View.elementSize.setValue(model.getElementSize());
 * View.measureSpace.setValue((int) model.getMeasureSpace());
 * View.titleFontSize.setValue((int) model.getTitleFontSize());
 * View.subtitleFontSize.setValue((int) model.getSubTitleFontSize());
 * View.leftMarginSpace.setValue((int) model.getLeftMargin());
 * View.rightMarginSpace.setValue((int) model.getLeftMargin());
 * View.pageList.setSelectedIndex(0);
 * View.propertiesPane.getVerticalScrollBar().setValue(0);
 * 
 * View.updateCorrection(model.getFilename());
 * 
 * // ELSE display the error message and don't enable buttons.
 * 
 * } catch (NoFileExistsException | CannotReadFileException | EmptyFileException
 * | NoMusicException | LargeNumberException | ConversionException e1) {
 * Controller.displayError(e1.getMessage()); } }
 * 
 * }
 */

class SpacingListener implements ChangeListener
{
	public void stateChanged(ChangeEvent e)
	{
		JSlider source = (JSlider) e.getSource();

		if (!source.getValueIsAdjusting())
		{

			Model model = Controller.getModel();
			try
			{
				/*
				 * If the slider value didn't change from the current value then
				 * do nothing
				 */
				if (model.getSpacing() != View.staffSpacing.getValue())
				{
					model.setSpacing(View.staffSpacing.getValue());
					model.converter
							.updateSpacing((View.staffSpacing.getValue()));
					IMGCreator.createPreview(model);

					// CHECK IF CONVERSION WAS DONE PROPERLY.

					String image = IMGCreator.getLastConverted();
					View.repaintPreview(image);
				}

			} catch (ConversionException e1)
			{
				Controller.displayError(e1.getMessage());
			}

		}

	}
}

class ElementSizeListener implements ChangeListener
{

	public void stateChanged(ChangeEvent e)
	{

		JSlider source = (JSlider) e.getSource();

		if (!source.getValueIsAdjusting())
		{

			Model model = Controller.getModel();
			try
			{
				/*
				 * If the slider value didn't change from the current value then
				 * do nothing
				 */
				if (model.getElementSize() != View.elementSize.getValue())
				{
					model.setElementSize(View.elementSize.getValue());
					model.converter.updateElementSize((View.elementSize
							.getValue()));
					IMGCreator.createPreview(model);

					// CHECK IF CONVERSION WAS DONE PROPERLY.

					String image = IMGCreator.getLastConverted();
					View.repaintPreview(image);
				}

			} catch (ConversionException e1)
			{
				Controller.displayError(e1.getMessage());
			}

		}

	}
}

class MeasureSpaceListener implements ChangeListener
{

	public void stateChanged(ChangeEvent e)
	{

		JSlider source = (JSlider) e.getSource();

		if (!source.getValueIsAdjusting())
		{

			Model model = Controller.getModel();
			try
			{
				/*
				 * If the slider value didn't change from the current value then
				 * do nothing
				 */
				if (model.getMeasureSpace() != View.measureSpace.getValue())
				{
					model.setMeasureSpace(View.measureSpace.getValue());
					model.converter.updateMeasureSpace((View.measureSpace
							.getValue()));
					IMGCreator.createPreview(model);

					// CHECK IF CONVERSION WAS DONE PROPERLY.

					String image = IMGCreator.getLastConverted();
					View.repaintPreview(image);
				}

			} catch (ConversionException e1)
			{
				Controller.displayError(e1.getMessage());
			}

		}

	}
}

class PageSizeListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Model model = Controller.getModel();
		try
		{
			/* Get the corresponding rectangle from the GUI page size */
			Rectangle pagesize = model
					.convertPageSizeToRectangle((String) View.pageList
							.getSelectedItem());
			String ps = model.convertPageSizeToString(model.getPageSize());

			/* If the value didn't change from the current value then do nothing */
			if (!ps.equals((String) View.pageList.getSelectedItem()))
			{
				model.setPageSize(pagesize);
				model.converter.updatePageSize(pagesize);
				IMGCreator.createPreview(model);

				// CHECK IF CONVERSION WAS DONE PROPERLY.

				String image = IMGCreator.getLastConverted();
				View.repaintPreview(image, pagesize);
			}

		} catch (ConversionException e1)
		{
			Controller.displayError(e1.getMessage());
		}

	}
}

class TitleFontSizeListener implements ChangeListener
{

	public void stateChanged(ChangeEvent e)
	{

		JSlider source = (JSlider) e.getSource();

		if (!source.getValueIsAdjusting())
		{

			Model model = Controller.getModel();
			try
			{
				/*
				 * If the slider value didn't change from the current value then
				 * do nothing
				 */
				if (model.getTitleFontSize() != View.titleFontSize.getValue())
				{
					model.setTitleFontSize(View.titleFontSize.getValue());
					model.converter.updateTitleSize((View.titleFontSize
							.getValue()));
					IMGCreator.createPreview(model);

					// CHECK IF CONVERSION WAS DONE PROPERLY.

					String image = IMGCreator.getLastConverted();
					View.repaintPreview(image);
				}

			} catch (ConversionException e1)
			{
				Controller.displayError(e1.getMessage());
			}

		}

	}
}

class SubtitleFontSizeListener implements ChangeListener
{

	public void stateChanged(ChangeEvent e)
	{

		JSlider source = (JSlider) e.getSource();

		if (!source.getValueIsAdjusting())
		{

			Model model = Controller.getModel();
			try
			{
				/*
				 * If the slider value didn't change from the current value then
				 * do nothing
				 */
				if (model.getSubTitleFontSize() != View.subtitleFontSize
						.getValue())
				{
					model.setSubTitleFontSize(View.subtitleFontSize.getValue());
					model.converter.updateSubtitleSize((View.subtitleFontSize
							.getValue()));
					IMGCreator.createPreview(model);

					// CHECK IF CONVERSION WAS DONE PROPERLY.

					String image = IMGCreator.getLastConverted();
					View.repaintPreview(image);
				}

			} catch (ConversionException e1)
			{
				Controller.displayError(e1.getMessage());
			}

		}

	}
}

class LeftMarginListener implements ChangeListener
{

	public void stateChanged(ChangeEvent e)
	{

		JSlider source = (JSlider) e.getSource();

		if (!source.getValueIsAdjusting())
		{

			Model model = Controller.getModel();
			try
			{
				/*
				 * If the slider value didn't change from the current value then
				 * do nothing
				 */
				if (model.getLeftMargin() != View.leftMarginSpace.getValue())
				{
					model.setLeftMargin(View.leftMarginSpace.getValue());
					model.converter.updateLeftMargin((View.leftMarginSpace
							.getValue()));
					IMGCreator.createPreview(model);

					// CHECK IF CONVERSION WAS DONE PROPERLY.

					String image = IMGCreator.getLastConverted();
					View.repaintPreview(image);
				}

			} catch (ConversionException e1)
			{
				Controller.displayError(e1.getMessage());
			}

		}

	}
}

class RightMarginListener implements ChangeListener
{

	public void stateChanged(ChangeEvent e)
	{

		JSlider source = (JSlider) e.getSource();

		if (!source.getValueIsAdjusting())
		{

			Model model = Controller.getModel();
			try
			{
				/*
				 * If the slider value didn't change from the current value then
				 * do nothing
				 */
				if (model.getRightMargin() != View.rightMarginSpace.getValue())
				{
					model.setRightMargin(View.rightMarginSpace.getValue());
					model.converter.updateRightMargin((View.rightMarginSpace
							.getValue()));
					IMGCreator.createPreview(model);

					// CHECK IF CONVERSION WAS DONE PROPERLY.

					String image = IMGCreator.getLastConverted();
					View.repaintPreview(image);
				}

			} catch (ConversionException e1)
			{
				Controller.displayError(e1.getMessage());
			}

		}

	}

}
