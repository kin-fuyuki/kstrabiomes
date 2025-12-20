package kn.kinfuyuki.kstrabiomes.biome;

import kn.kinfuyuki.kstrabiomes.biomerangemapacessor;
import kn.kinfuyuki.kstrabiomes.main;
import kn.kinfuyuki.kstrabiomes.mixin.biomeprovideroverworldaccessor;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.biome.data.BiomeRange;
import net.minecraft.core.world.biome.data.BiomeRangeMap;
import net.minecraft.core.world.biome.provider.BiomeProvider;
import net.minecraft.core.world.noise.PerlinSimplexNoise;
import net.minecraft.core.world.type.WorldType;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;

/*
x,y,z,xz,zy,yx,xyz

*/
public class biggerbiomesoverworld extends BiomeProvider {
	public static final BiomeRangeMap brm = new BiomeRangeMap();
	private final PerlinSimplexNoise temperatureNoise;
	private final PerlinSimplexNoise humidityNoise;
	private final PerlinSimplexNoise varietyNoise;
	private final PerlinSimplexNoise fuzzinessNoise;
	private double temperatureXScale = 0.00125;
	private double temperatureZScale = 0.00125;
	private double temperatureExponent = (double)0.25F;
	private double temperatureFuzzPercentage = 0.01;
	private double humidityXScale = 0.0025;
	private double humidityZScale = 0.0025;
	private double humidityExponent = 0.3;
	private double humidityFuzzPercentage = 0.01;
	private double varietyXScale = (double)0.025F;
	private double varietyZScale = (double)0.025F;
	private double varietyExponent = 0.3;
	private double varietyFuzzPercentage = (double)0.0F;
	private double fuzzinessXScale = (double)0.025F;
	private double fuzzinessZScale = (double)0.025F;
	private double fuzzinessExponent = (double)1.0F;
	private final WorldType worldType;

	public biggerbiomesoverworld(long seed, WorldType worldType) {
		this.worldType = worldType;
		this.temperatureNoise = new PerlinSimplexNoise(new Random(seed * 9871L), 4);
		this.humidityNoise = new PerlinSimplexNoise(new Random(seed * 39811L), 4);
		this.varietyNoise = new PerlinSimplexNoise(new Random(seed), 4);
		this.fuzzinessNoise = new PerlinSimplexNoise(new Random(seed * 543321L), 2);
	}
	static String[] namespaces={"minecraft"};
	static int muchnamespaces=1;

	public void addbiome(Biome biome, Map<Biome,BiomeRange[]> range){

	}
	public static void lock(){
		int current=1;
//18 biomes
		brm.clear();
		biomerangemapacessor map =(biomerangemapacessor)(Object) biomeprovideroverworldaccessor.getBrm();
		map.kstrabiomes$getRanges().forEach(
			new BiConsumer<Biome, Set<BiomeRange>>() {
				@Override
				public void accept(Biome biome, Set<BiomeRange> biomeRanges) {
					brm.addRange(biome, biomeRanges.toArray(new BiomeRange[]{}));
				}
			}
		);


		brm.lock();
		main.LOGGER.error("locking");
	}
	public Biome[] getBiomes(Biome[] biomes, double[] temperatures, double[] humidities, double[] varieties, int x, int y, int z, int xSize, int ySize, int zSize) {
		if (biomes == null || biomes.length < xSize * ySize * zSize) {
			biomes = new Biome[xSize * ySize * zSize];
		}

		if (temperatures == null || temperatures.length < xSize * zSize) {
			temperatures = this.getTemperatures(temperatures, x, z, xSize, zSize);
		}

		if (humidities == null || humidities.length < xSize * zSize) {
			humidities = this.getHumidities(humidities, x, z, xSize, zSize);
		}

		if (varieties == null || varieties.length < xSize * zSize) {
			varieties = this.getVarieties(varieties, x, z, xSize, zSize);
		}

		for(int dx = 0; dx < xSize; ++dx) {
			for(int dz = 0; dz < zSize; ++dz) {
				double temperature = temperatures[dx * zSize + dz];
				double humidity = humidities[dx * zSize + dz];
				double variety = varieties[dx * zSize + dz];

				for(int dy = 0; dy < ySize; ++dy) {
					double altitude = this.worldType.getYPercentage(y + dy << 3);
					biomes[dy * xSize * zSize + dz * xSize + dx] = this.lookupBiome(temperature, humidity, altitude, variety);
				}
			}
		}

		return biomes;
	}

	public double[] getTemperatures(double[] temperatures, int x, int z, int xSize, int zSize) {
		if (temperatures == null || temperatures.length < xSize * zSize) {
			temperatures = new double[xSize * zSize];
		}

		double[] tnResult = this.temperatureNoise.getValue((double[])null, (double)x, (double)z, xSize, zSize, this.temperatureXScale, this.temperatureZScale, this.temperatureExponent);
		double[] fnResult = this.fuzzinessNoise.getValue((double[])null, (double)x, (double)z, xSize, zSize, this.fuzzinessXScale, this.fuzzinessZScale, this.fuzzinessExponent);

		for(int dx = 0; dx < xSize; ++dx) {
			for(int dz = 0; dz < zSize; ++dz) {
				double fuzziness = fnResult[dx * zSize + dz] * 1.1 + (double)0.5F;
				double fuzzPctg = this.temperatureFuzzPercentage;
				double valPctg = (double)1.0F - fuzzPctg;
				double temperature = (tnResult[dx * zSize + dz] * 0.15 + 0.7) * valPctg + fuzziness * fuzzPctg;
				if (temperature < (double)0.0F) {
					temperature = (double)0.0F;
				}

				if (temperature > (double)1.0F) {
					temperature = (double)1.0F;
				}

				temperatures[dx * zSize + dz] = temperature;
			}
		}

		return temperatures;
	}

	public double[] getHumidities(double[] humidities, int x, int z, int xSize, int zSize) {
		if (humidities == null || humidities.length < xSize * zSize) {
			humidities = new double[xSize * zSize];
		}

		double[] hnResult = this.humidityNoise.getValue((double[])null, (double)x, (double)z, xSize, zSize, this.humidityXScale, this.humidityZScale, this.humidityExponent);
		double[] fnResult = this.fuzzinessNoise.getValue((double[])null, (double)x, (double)z, xSize, zSize, this.fuzzinessXScale, this.fuzzinessZScale, this.fuzzinessExponent);

		for(int dx = 0; dx < xSize; ++dx) {
			for(int dz = 0; dz < zSize; ++dz) {
				double fuzziness = fnResult[dx * zSize + dz] * 1.1 + (double)0.5F;
				double fuzzPctg = this.humidityFuzzPercentage;
				double valPctg = (double)1.0F - fuzzPctg;
				double humidity = (hnResult[dx * zSize + dz] * 0.15 + (double)0.5F) * valPctg + fuzziness * fuzzPctg;
				if (humidity < (double)0.0F) {
					humidity = (double)0.0F;
				}

				if (humidity > (double)1.0F) {
					humidity = (double)1.0F;
				}

				humidities[dx * zSize + dz] = humidity;
			}
		}

		return humidities;
	}

	public double[] getVarieties(double[] varieties, int x, int z, int xSize, int zSize) {
		if (varieties == null || varieties.length < xSize * zSize) {
			varieties = new double[xSize * zSize];
		}

		double[] vnResult = this.varietyNoise.getValue((double[])null, (double)x, (double)z, xSize, zSize, this.varietyXScale, this.varietyZScale, this.varietyExponent);
		double[] fnResult = this.fuzzinessNoise.getValue((double[])null, (double)x, (double)z, xSize, zSize, this.fuzzinessXScale, this.fuzzinessZScale, this.fuzzinessExponent);

		for(int dx = 0; dx < xSize; ++dx) {
			for(int dz = 0; dz < zSize; ++dz) {
				double fuzziness = fnResult[dx * zSize + dz] * 1.1 + (double)0.5F;
				double fuzzPctg = this.varietyFuzzPercentage;
				double valPctg = (double)1.0F - fuzzPctg;
				double variety = (vnResult[dx * zSize + dz] * 0.15 + (double)0.5F) * valPctg + fuzziness * fuzzPctg;
				if (variety < (double)0.0F) {
					variety = (double)0.0F;
				}

				if (variety > (double)1.0F) {
					variety = (double)1.0F;
				}

				varieties[dx * zSize + dz] = variety;
			}
		}

		return varieties;
	}

	public double[] getBiomenesses(double[] biomenesses, int x, int y, int z, int xSize, int ySize, int zSize) {
		if (biomenesses == null || biomenesses.length < xSize * ySize * zSize) {
			biomenesses = new double[xSize * ySize * zSize];
		}

		double[] temperatures = this.getTemperatures((double[])null, x, z, xSize, zSize);
		double[] humidities = this.getHumidities((double[])null, x, z, xSize, zSize);
		double[] varieties = this.getVarieties((double[])null, x, z, xSize, zSize);

		for(int dx = 0; dx < xSize; ++dx) {
			for(int dy = 0; dy < ySize; ++dy) {
				for(int dz = 0; dz < zSize; ++dz) {
					double temperature = MathHelper.clamp(temperatures[dx * zSize + dz], (double)0.0F, (double)1.0F);
					double humidity = MathHelper.clamp(humidities[dx * zSize + dz], (double)0.0F, (double)1.0F);
					double altitude = MathHelper.clamp(this.worldType.getYPercentage(y + dy << 3), (double)0.0F, (double)1.0F);
					double variety = MathHelper.clamp(varieties[dx * zSize + dz], (double)0.0F, (double)1.0F);
					Biome biome = this.lookupBiome(temperature, humidity, altitude, variety);
					Set<BiomeRange> ranges = brm.getRanges(biome);
					humidity *= temperature;
					double biomeness = (double)0.0F;

					for(BiomeRange range : ranges) {
						if (range.contains(temperature, humidity, variety, altitude)) {
							double temperatureRange = range.getMaxTemperature() - range.getMinTemperature();
							double humidityRange = range.getMaxHumidity() - range.getMinHumidity();
							double altitudeRange = range.getMaxAltitude() - range.getMinAltitude();
							double varietyRange = range.getMaxVariety() - range.getMinVariety();
							double newTemperature = (temperature - range.getMinTemperature()) / temperatureRange;
							double newHumidity = (humidity - range.getMinHumidity()) / humidityRange;
							double newAltitude = (altitude - range.getMinAltitude()) / altitudeRange;
							double newVariety = (variety - range.getMinVariety()) / varietyRange;
							if ((!(range.getMinTemperature() <= (double)0.0F) || !(newTemperature <= (double)0.5F)) && (!(range.getMaxTemperature() >= (double)1.0F) || !(newTemperature >= (double)0.5F))) {
								newTemperature = -Math.abs(newTemperature * (double)2.0F - (double)1.0F) + (double)1.0F;
							} else {
								newTemperature = (double)1.0F;
							}

							if ((!(range.getMinHumidity() <= (double)0.0F) || !(newHumidity <= (double)0.5F)) && (!(range.getMaxHumidity() >= (double)1.0F) || !(newHumidity >= (double)0.5F))) {
								newHumidity = -Math.abs(newHumidity * (double)2.0F - (double)1.0F) + (double)1.0F;
							} else {
								newHumidity = (double)1.0F;
							}

							if ((!(range.getMinAltitude() <= (double)0.0F) || !(newAltitude <= (double)0.5F)) && (!(range.getMaxAltitude() >= (double)1.0F) || !(newAltitude >= (double)0.5F))) {
								newAltitude = -Math.abs(newAltitude * (double)2.0F - (double)1.0F) + (double)1.0F;
							} else {
								newAltitude = (double)1.0F;
							}

							if ((!(range.getMinVariety() <= (double)0.0F) || !(newVariety <= (double)0.5F)) && (!(range.getMaxVariety() >= (double)1.0F) || !(newVariety >= (double)0.5F))) {
								newVariety = -Math.abs(newVariety * (double)2.0F - (double)1.0F) + (double)1.0F;
							} else {
								newVariety = (double)1.0F;
							}

							double newBiomeness = newTemperature * newHumidity * newAltitude * newVariety;
							if (newBiomeness > biomeness) {
								biomeness = newBiomeness;
							}
						}
					}

					biomenesses[dy * xSize * zSize + dz * xSize + dx] = biomeness;
				}
			}
		}

		return biomenesses;
	}

	public Biome lookupBiome(double temperature, double humidity, double variety, double altitude) {
		humidity *= temperature;

		return brm.lookupBiome(temperature, humidity, variety, altitude);
	}
	public static void init() {

	}
}
