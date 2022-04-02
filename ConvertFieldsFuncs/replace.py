import sys, os

def main():
	if len(sys.argv) < 2:
		raise Exception("Not enough params")

	file = sys.argv[1]

	reverse = False
	if len(sys.argv) > 2:
		if sys.argv[2].lower() == "-r":
			reverse = True

	replaceFile(file, reverse)


def isFile(file):
	if not os.path.isfile(file):
		raise Exception(f"File not found '{file}'")


def replaceFile(file, reverse):
	replacement = Replacement(file, reverse)

	replacement.addReplacementFile("fields.csv")
	replacement.addReplacementFile("methods.csv")
	replacement.addReplacementFile("params.csv")

	replacement.testFiles()
	replacement.replaceData()

	replacement.writeToFile()

class Replacement:
	def __init__(self, file, reverse):
		self.replacementFiles = []
		self.file = file
		self.reverse = reverse
		self.data = open(self.file, "r").read()

	def addReplacementFile(self, file):
		self.replacementFiles.append(file)

	def replaceData(self):
		for file in self.replacementFiles:
			for line in open(file).read().split("\n"):
				s = line.split(",")
				if self.reverse:
					self.data = self.data.replace(s[1], s[0])
				else:
					self.data = self.data.replace(s[0], s[1])

	def testFiles(self):
		isFile(self.file)
		for x in self.replacementFiles:
			isFile(self.file)

	def writeToFile(self):
		open(self.file, "w").write(self.data)
		print(f"Sucessfully replaced '{self.file}'")


if __name__ == "__main__":
	main()


