
import org.apache.commons.imaging.ImageReadException
import org.apache.commons.imaging.Imaging
import org.apache.commons.imaging.common.ImageMetadata
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata
import org.apache.commons.imaging.formats.tiff.TiffField
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo
import java.io.File
import java.io.IOException
import kotlin.io.path.Path


fun main(args: Array<String>) {
    val from = Path("/Volumes/Untitled/DCIM/100MSDCF")

    from.file
    metadataExample(File("/Users/bernhardfuchs/Downloads/IMG_20221025_165133.jpg"))

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}


@Throws(ImageReadException::class, IOException::class)
fun metadataExample(file: File) {
    // get all metadata stored in EXIF format (ie. from JPEG or TIFF).
    val metadata: ImageMetadata = Imaging.getMetadata(file)

    // System.out.println(metadata);
    if (metadata is JpegImageMetadata) {
        val jpegMetadata: JpegImageMetadata = metadata as JpegImageMetadata

        val modelTag = jpegMetadata.findEXIFValue(TagInfo("Model", 272, FieldType.ASCII))
        val model = modelTag.stringValue
        val dateTag = jpegMetadata.findEXIFValueWithExactMatch( TiffTagConstants.TIFF_TAG_DATE_TIME)
        val date = dateTag.stringValue

        val date2 = date.replace(":", "-").replace(" ", "_")
        val newName = "$date2 $model.jpeg"

        //val items: List<ImageMetadata.ImageMetadataItem> = jpegMetadata.getItems()
        //for (item in items) {
            //println("    item: $item.")
        //}
        println(newName)
    }
}

private fun printTagValue(jpegMetadata: JpegImageMetadata,
                          tagInfo: TagInfo) {
    val field: TiffField? = jpegMetadata.findEXIFValueWithExactMatch(tagInfo)
    if (field==null) {
        println(tagInfo.name + ": " + "Not Found.")
    } else {
        println(tagInfo.name + ": "
                + field.getValueDescription())
    }
}