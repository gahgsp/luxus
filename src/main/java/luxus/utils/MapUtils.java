package luxus.utils;

public class MapUtils {

    public static int[] reverseChunkData(int[] chunkToReverse) {
        int[] reversedChunk = new int[chunkToReverse.length];
        int originalChunkLength = chunkToReverse.length;
        for (int index = 0; index < chunkToReverse.length; index++) {
            reversedChunk[originalChunkLength - 1] = chunkToReverse[index];
            originalChunkLength = originalChunkLength - 1;
        }
        return reversedChunk;
    }
}
