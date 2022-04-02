import os, sys
from replace import replaceFile

def main():
	if len(sys.argv) < 2:
		raise Exception("Not enough params")

	directory = sys.argv[1]

	if not os.path.isdir(directory):
		raise Exception(f"Can't find directory '{directory}'")

	reverse = False
	if len(sys.argv) > 2:
		if sys.argv[2].lower() == "-r":
			reverse = True

	replaceFilesInDir(directory, reverse)


def replaceFilesInDir(path, reverse):
	if not os.path.isdir(path):
		return

	for x in os.listdir(path):
		joinedPath = pathJoin(path, x)

		if os.path.isdir(joinedPath):
			replaceFilesInDir(joinedPath, reverse)

		elif os.path.isfile(joinedPath):
			#print(f"Replacing file {joinedPath}..")
			replaceFile(joinedPath, reverse)

def pathJoin(path, file):
	return f"{path}/{file}"

if __name__ == "__main__":
	main()